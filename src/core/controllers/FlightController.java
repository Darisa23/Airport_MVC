/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.validators.DateUtils;
import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.controllers.utils.validators.FlightValidator;
import core.controllers.utils.validators.ValidationUtils;
import core.models.Flight;
import core.models.Location;
import core.models.Observers.Observer;
import core.models.Passenger;
import core.models.Plane;
import core.models.storage.StorageFlights;
import core.services.FlightService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Alexander Sanguino
 */
public class FlightController {
    private final PassengerController passContr;
    private final FlightService flightService; // Declaración
    private final PlaneController planeController;
    private final LocationController locationController;


    public FlightController() {
        this.passContr = new PassengerController();
        this.flightService = new FlightService(); // Inicialización aquí
        this.planeController = new PlaneController();
        this.locationController = new LocationController();
    }
    
    public Response registerFlight(String id, String planeId, String departureLocationId, String arrivalLocationId, String scaleLocationId,
                                  String year, String month, String day, String hour, String minutes,
                                  String hoursDurationArrival, String minutesDurationArrival,
                                  String hoursDurationScale, String minutesDurationScale) {
        try {
            // 1. Validaciones de presencia (campos vacíos)
            if (ValidationUtils.anyEmpty(id, planeId, departureLocationId, arrivalLocationId, year, month, day, hour, minutes,
                                          hoursDurationArrival, minutesDurationArrival)) {
                return new Response("Required fields cannot be empty", Status.BAD_REQUEST);
            }

            // 2. Validaciones de formato y otras reglas específicas (con FlightValidator)
            Response validationResponse = FlightValidator.INSTANCE.isValid(id, planeId, departureLocationId, arrivalLocationId, scaleLocationId, year, month, day, hour, minutes,
                                                                          hoursDurationArrival, minutesDurationArrival, hoursDurationScale, minutesDurationScale);
            if (validationResponse.getStatus() != Status.OK) {
                return validationResponse;
            }

            // 3. Parseo de Strings a Integer para duraciones.
            int parsedHoursDurationArrival, parsedMinutesDurationArrival;
            int parsedHoursDurationScale = 0, parsedMinutesDurationScale = 0; // Inicializar para el caso sin escala
            try {
                parsedHoursDurationArrival = Integer.parseInt(hoursDurationArrival);
                parsedMinutesDurationArrival = Integer.parseInt(minutesDurationArrival);
                // Solo parsear duración de escala si scaleLocationId no es vacío/nulo y no es el valor por defecto
                if (scaleLocationId != null && !scaleLocationId.trim().isEmpty() && !scaleLocationId.equals("Location")) {
                    parsedHoursDurationScale = Integer.parseInt(hoursDurationScale);
                    parsedMinutesDurationScale = Integer.parseInt(minutesDurationScale);
                }
            } catch (NumberFormatException e) {
                return new Response("Invalid number format for duration times.", Status.BAD_REQUEST);
            }

            // 4. Validación de negocio: Verificar si el vuelo ya existe
            if (flightService.getFlight(id)!=null ) {
                return new Response("Flight with this ID already exists", Status.BAD_REQUEST);
            }

            // 5. Obtener y validar objetos relacionados (Plane, Location). Esto es responsabilidad del controlador.
            Plane plane = (Plane) planeController.getPlane(planeId).getObject();
            Location departureLocation = (Location) locationController.getAirport(departureLocationId).getObject();
            Location arrivalLocation = (Location) locationController.getAirport(arrivalLocationId).getObject();

            if (plane == null) {
                 return new Response("Plane not found for ID: " + planeId, Status.BAD_REQUEST);
            }
            if (departureLocation == null) {
                 return new Response("Departure location not found for ID: " + departureLocationId, Status.BAD_REQUEST);
            }
            if (arrivalLocation == null) {
                 return new Response("Arrival location not found for ID: " + arrivalLocationId, Status.BAD_REQUEST);
            }

            Location scaleLocation = null;
            if (scaleLocationId != null && !scaleLocationId.trim().isEmpty() && !scaleLocationId.equals("Location")) {
                scaleLocation = (Location) locationController.getAirport(scaleLocationId).getObject();
                if (scaleLocation == null) {
                    return new Response("Scale location not found for ID: " + scaleLocationId, Status.BAD_REQUEST);
                }
            }

            // 6. Si todas las validaciones pasaron, llamar al servicio.
            Flight newFlight = flightService.registerFlight(
                    id, plane, departureLocation, arrivalLocation, scaleLocation,
                    year, month, day, hour, minutes,
                    parsedHoursDurationArrival, parsedMinutesDurationArrival,
                    parsedHoursDurationScale, parsedMinutesDurationScale);

            return new Response("Flight created successfully", Status.CREATED, newFlight);

        } catch (IllegalArgumentException e) {
            // Captura errores de formato o validación lanzados por el servicio (como fallback).
            return new Response("Invalid data provided: " + e.getMessage(), Status.BAD_REQUEST);
        } catch (IllegalStateException e) {
            // Captura errores de lógica de negocio o persistencia lanzados por el servicio.
            return new Response("Internal server error during flight registration: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            // Captura cualquier otra excepción inesperada.
            return new Response("An unexpected error occurred: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }


    //obtener todos los id de vuelos:
    public Response getAllFlightsIds() {
    return new Response("Flights IDs retrieved", Status.OK, flightService.allFlights());
    }
    

    //Agregar un vuelo:
    public static Response addFlight(Flight flight) {
        try {
            boolean added = StorageFlights.getInstance().add(flight);
            if (!added) {
                return new Response("This flight already exists", Status.BAD_REQUEST);
            }
            return new Response("Flight added successfully", Status.OK);
        } catch (Exception e) {
            return new Response("Error retrieving flight: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    //Añadirle un pasajero a un vuelo:
    public Response addPassengertoFlight(String flightId, String passengerId) {
        try {
            System.out.println("intentando con vuelo: "+flightId+" y pasajero: "+passengerId);
            //revisar que si seleccionó un Id de usuario:
            if(passengerId.isEmpty()){
            return new Response("You must select an User id in order to add a flight", Status.NO_CONTENT);
        }
            Flight flight = flightService.getFlight(flightId);
            if(flight== null) {
                return new Response("Flight not found", Status.NOT_FOUND);
            }
            if(!flightService.hasAviableSeats(flightId)){
                return new Response("This Flight is fully booked", Status.NOT_FOUND);
            }
          
            Response opassenger = passContr.addToFlight(passengerId,flight);
        
            if (opassenger.getStatus() == Status.NOT_FOUND |opassenger.getStatus() == Status.BAD_REQUEST) {
                System.out.println("hola");
                return opassenger;
            }
            Passenger passenger = (Passenger) opassenger.getObject();

            boolean added = flight.addPassenger(passenger);
            if (!added) {
                System.out.println("el pasajero "+passengerId+"/"+passenger.getFirstname()+" no se agregó");
                return new Response("Passenger is already on this flight", Status.BAD_REQUEST);
            }
            flightService.addPassenger(flightId, passenger);
            return new Response("Passenger was successfully added to flight", Status.BAD_REQUEST);

        } catch (Exception e) {
            return new Response("Error adding passenger to flight: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    //Actualiza un avión
      public Response delayFlight(String id, String hours, String minutes  ) {
        try {
            //revisar si vuelo existe:
            
            if (flightService.getFlight(id)==null) {
                return new Response("flight does not exist", Status.NOT_FOUND);
            }
           if (!DateUtils.isValidHour(hours)&& !DateUtils.isValidMinute(minutes)){
               return new Response("invalid hours or minutes", Status.BAD_REQUEST);
           }
               flightService.delay(id, hours, minutes);
         
            return new Response("delayed succesfully", Status.OK);
            
           
        } catch (Exception e) {
            return new Response("Error adding passenger to flight: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }}



public Response getFlights() {
    return new Response("flights refreshed", Status.OK, flightService.completeInfo());
}

       public void registerObserver(Observer observer) {
        flightService.addObserver(observer);
    }
}


