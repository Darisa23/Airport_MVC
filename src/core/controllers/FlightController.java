/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.validators.DateUtils;
import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Flight;
import core.models.Passenger;
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
    private final PassengerController passContr; // ¡Aquí está de vuelta!
    private final FlightService flightService; // Mantenemos el FlightService

    public FlightController() {
        this.passContr = new PassengerController(); // ¡Y su inicialización!
        this.flightService = new FlightService(); // Inicializamos el FlightService
    }

    public Response registerFlight(String id, String plane, String departureLocation, String arrivalLocation, String scaleLocation,
                                  String year, String month, String day, String hour, String minutes,
                                  String hoursDurationArrival, String minutesDurationArrival,
                                  String hoursDurationScale, String minutesDurationScale) {
        // Delega al FlightService, como habíamos acordado
        return flightService.registerFlight(id, plane, departureLocation, arrivalLocation, scaleLocation, year, month, day, hour, minutes,
                                            hoursDurationArrival, minutesDurationArrival, hoursDurationScale, minutesDurationArrival); // Ojo con 'minutesDurationArrival' al final si es 'minutesDurationScale'
    }

//Obtener un vuelo:
    public static Response getFlight(String id) {
        try {
            Flight flight = StorageFlights.getInstance().get(id);

            if (flight == null) {
                return new Response("Flight not found", Status.NOT_FOUND);
            }
            Flight copyFlight = flight.copy();
            return new Response("Flight retrieved successfully", Status.OK, flight);
        } catch (Exception e) {
            return new Response("Error retrieving flight: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    //obtener todos los vuelos:
    public Response getAllFlights() {
        try {
            ArrayList<Flight> flights = StorageFlights.getInstance().getAll();
            Collections.sort(flights, Comparator.comparing(Flight::getDepartureDate));
            //acá que devuelva copia:
            return new Response("Flights retrieved successfully", Status.OK, flights);
        } catch (Exception e) {
            return new Response("Error retrieving flights list: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }
    //obtener todos los id de vuelos:
    public Response getAllFlightIds() {
    ArrayList<Flight> flights = StorageFlights.getInstance().getAll();
    List<String> ids = flights.stream()
                                 .map(p -> String.valueOf(p.getId()))
                                 .toList(); 
    return new Response("Flights IDs retrieved", Status.OK, ids);
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
            //revisar que si seleccionó un Id de usuario:
            if(passengerId.isEmpty()){
            return new Response("Debe seleccionar un Id de usuario para agregar un vuelo", Status.NO_CONTENT);
        }
            System.out.println("reviso si el vuelo existe");
            Response oflight = getFlight(flightId);
            if (oflight.getStatus() == Status.NOT_FOUND) {
                return oflight;
            }
            System.out.println("como si existe lo tomo del storage");
            Flight flight = StorageFlights.getInstance().get(flightId);
            Response opassenger = passContr.addToFlight(passengerId,flight);
            System.out.println("la respuesta de ir al otro coso: "+opassenger.getMessage());
            if (opassenger.getStatus() == Status.NOT_FOUND |opassenger.getStatus() == Status.BAD_REQUEST) {
                return opassenger;
            }
            Passenger passenger = (Passenger) opassenger.getObject();

            boolean added = flight.addPassenger(passenger);
            if (!added) {
                return new Response("Passenger is already on this flight", Status.BAD_REQUEST);
            }
            System.out.println("no estaba agregado el pasajero: "+passenger.getFirstname()+ " se agregó");
            return new Response("Passenger was successfully added to flight", Status.BAD_REQUEST);

        } catch (Exception e) {
            return new Response("Error adding passenger to flight: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    //Actualiza un avión
      public Response delayFlight(String id, String hours, String minutes  ) {
        try {
            //revisar si vuelo existe:
             Response oflight = getFlight(id);
            if (oflight.getStatus() == Status.NOT_FOUND) {
                return new Response("flight does not exist", Status.NOT_FOUND);
            }
           if (!DateUtils.isValidHour(hours)&& !DateUtils.isValidMinute(minutes)){
               return new Response("invalid hours or minutes", Status.BAD_REQUEST);
           }
     
            Flight flight = StorageFlights.getInstance().get(id);
            
            flight.delay(Integer.parseInt(hours),Integer.parseInt(minutes));
            StorageFlights.getInstance().update(flight);
            return new Response("delayed succesfully", Status.OK);
            
           
        } catch (Exception e) {
            return new Response("Error adding passenger to flight: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }}

}

