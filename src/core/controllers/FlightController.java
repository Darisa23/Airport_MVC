/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import static core.controllers.PlaneController.getPlane;
import core.controllers.utils.validators.DateUtils;
import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.controllers.utils.validators.FlightValidator;
import core.controllers.utils.validators.PassengerValidator;
import core.controllers.utils.validators.PlaneValidator;
import core.controllers.utils.validators.ValidationUtils;
import core.models.Flight;
import core.models.Location;
import core.models.Passenger;
import core.models.Plane;
import core.models.storage.StorageFlights;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Alexander Sanguino
 */
public class FlightController {

    public Response registerFlight(String id, String plane, String departureLocation, String arrivalLocation, String scaleLocation,
            String year, String month, String day, String hour, String minutes,
            String hoursDurationArrival, String minutesDurationArrival,
            String hoursDurationScale, String minutesDurationScale) {
        try {
            //Validaciones:
            Response Invalid = FlightValidator.INSTANCE.isValid(id, plane, departureLocation, arrivalLocation, scaleLocation, year, month, day, hour, minutes, hoursDurationArrival, minutesDurationArrival, hoursDurationScale, minutesDurationScale);
            if (Invalid.getStatus() != Status.OK) {
                return Invalid;
            }
            // 11. Create flight object
            Flight flight;
            if (scaleLocation != null && !scaleLocation.trim().isEmpty()) {
                flight = new Flight(id,
                        (Plane) PlaneController.getPlane(plane).getObject(),
                        (Location) LocationController.getAirport(departureLocation).getObject(),
                        (Location) LocationController.getAirport(scaleLocation).getObject(),
                        (Location) LocationController.getAirport(arrivalLocation).getObject(),
                        DateUtils.buildDate(year, month, day, hour, minutes),
                        Integer.parseInt(hoursDurationArrival),
                        Integer.parseInt(minutesDurationArrival),
                        Integer.parseInt(hoursDurationScale),
                        Integer.parseInt(minutesDurationScale));
            } else {
                flight = new Flight(id,
                        (Plane) PlaneController.getPlane(plane).getObject(),
                        (Location) LocationController.getAirport(departureLocation).getObject(),
                        (Location) LocationController.getAirport(arrivalLocation).getObject(),
                        DateUtils.buildDate(year, month, day, hour, minutes),
                        Integer.parseInt(hoursDurationArrival),
                        Integer.parseInt(minutesDurationArrival));
            }
            // 12. Add flight to storage

            addFlight(flight);
            return new Response("Flight created successfully", Status.CREATED, flight);

        } catch (Exception e) {
            return new Response("Error creating flight: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
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
            //revisar si el vuelo existe:
            System.out.println("el flight id: "+flightId);
                       System.out.println("el passenger id: "+passengerId);

            System.out.println("reviso si el vuelo existe");
            Response oflight = getFlight(flightId);
            if (oflight.getStatus() == Status.NOT_FOUND) {
                return oflight;
            }
            System.out.println("como si existe lo tomo del storage");
            Flight flight = StorageFlights.getInstance().get(flightId);
            Response opassenger = PassengerController.addToFlight(passengerId,flight);
            System.out.println("la respuesta de ir al otro coso: "+opassenger.getMessage());
            if (opassenger.getStatus() == Status.NOT_FOUND |opassenger.getStatus() == Status.BAD_REQUEST) {
                return opassenger;
            }
            Passenger passenger = (Passenger) opassenger.getObject();

            boolean added = flight.addPassenger(passenger);
            System.out.println("el added tiró: "+added);
            if (!added) {
                System.out.println("no mamita");
                return new Response("Passenger is already on this flight", Status.BAD_REQUEST);
            }
            System.out.println("no estaba agregado el pasajero: "+passenger.getFirstname()+ " se agregó");
            return new Response("Passenger was successfully added to flight", Status.BAD_REQUEST);

        } catch (Exception e) {
            return new Response("Error adding passenger to flight: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    //Actualiza un avión
    public static Response updateFlight(String id, String newplane, String newdepartureLocation, String newarrivalLocation, String newscaleLocation,
            String newyear, String newmonth, String newday, String newhour, String newminutes,
            String newhoursDurationArrival, String newminutesDurationArrival,
            String newhoursDurationScale, String newminutesDurationScale) {
        try {
            // 1. ver si el vuelo existe:
            Response flightRes = getFlight(id);

            if (flightRes.getStatus() == Status.NOT_FOUND) {
                return flightRes;
            }
            // como existe obtienes ese vuelo:
            Flight flight = (Flight) flightRes.getObject();
            // ver si los nuevos datos son válidos:
            Response Invalid = FlightValidator.INSTANCE.isValid(id, newplane, newdepartureLocation, newarrivalLocation, newscaleLocation, newyear, newmonth, newday,
                    newhour, newminutes, newhoursDurationArrival, newminutesDurationArrival, newhoursDurationScale, newminutesDurationScale
            );
            if (Invalid.getStatus() != Status.OK) {
                return Invalid;
            }
            //como los datos son válidos creamos el vuelo

            if (newscaleLocation != null && !newscaleLocation.trim().isEmpty()) {
                flight.setPlane((Plane) PlaneController.getPlane(newplane).getObject());
                flight.setDepartureLocation((Location) LocationController.getAirport(newdepartureLocation).getObject());
                flight.setArrivalLocation((Location) LocationController.getAirport(newarrivalLocation).getObject());
                flight.setScaleLocation((Location) LocationController.getAirport(newscaleLocation).getObject());
                flight.setDepartureDate(DateUtils.buildDate(newyear, newmonth, newday, newhour, newminutes));
                flight.setHoursDurationArrival(Integer.parseInt(newhoursDurationArrival));
                flight.setMinutesDurationArrival(Integer.parseInt(newminutesDurationArrival));
                flight.setHoursDurationScale(Integer.parseInt(newhoursDurationScale));
                flight.setMinutesDurationScale(Integer.parseInt(newminutesDurationScale));
            } else {
                flight.setPlane((Plane) PlaneController.getPlane(newplane).getObject());
                flight.setDepartureLocation((Location) LocationController.getAirport(newdepartureLocation).getObject());
                flight.setArrivalLocation((Location) LocationController.getAirport(newarrivalLocation).getObject());
                flight.setDepartureDate(DateUtils.buildDate(newyear, newmonth, newday, newhour, newminutes));
                flight.setHoursDurationArrival(Integer.parseInt(newhoursDurationArrival));
                flight.setMinutesDurationArrival(Integer.parseInt(newminutesDurationArrival));
            }
            return new Response("Plane updated successfully", Status.OK);

        } catch (Exception e) {
            return new Response("Error updating plane: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }
}
