/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.validators.DateUtils;
import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.controllers.utils.validators.FlightValidator;
import core.controllers.utils.validators.PassengerValidator;
import core.controllers.utils.validators.ValidationUtils;
import core.models.Flight;
import core.models.Location;
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
            Response Invalid = FlightValidator.INSTANCE.isValid(id,plane,departureLocation,arrivalLocation, scaleLocation,year,month, day, hour, minutes,hoursDurationArrival,  minutesDurationArrival,hoursDurationScale, minutesDurationScale);
            if (Invalid.getStatus()!=Status.OK){
                return Invalid;
            }
            // 11. Create flight object
            Flight flight;
            if (scaleLocation != null && !scaleLocation.trim().isEmpty()) {
                flight = new Flight(id,
                        (Plane) planeRes.getObject(),
                        (Location) deLocRes.getObject(),
                        (Location) scaleLocRes.getObject(),
                        (Location) arrLocRes.getObject(),
                        departureDate,
                        hoursDurArr,
                        minutesDurArr,
                        scaleHours,
                        scaleMinutes);
            } else {
                flight = new Flight(id,
                        (Plane) planeRes.getObject(),
                        (Location) deLocRes.getObject(),
                        (Location) arrLocRes.getObject(),
                        departureDate,
                        hoursDurArr,
                        minutesDurArr);
            }
            // 12. Add flight to storage

            addFlight(flight);
            return new Response("Flight created successfully", Status.CREATED, flight);

        } catch (Exception e) {
            return new Response("Error creating flight: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response getFlight(String id) {
        try {
            Flight flight = StorageFlights.getInstance().get(id);   
            
            if (flight == null) {
                return new Response("Flight not found", Status.NOT_FOUND);
            }
            Flight copyFlight = flight.copy();
            return new Response("Flight retrieved successfully", Status.OK, copyFlight);
        } catch (Exception e) {
            return new Response("Error retrieving flight: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    public Response getAllFlights() {
        try {
            ArrayList<Flight> flights = StorageFlights.getInstance().getAll();
            Collections.sort(flights, Comparator.comparing(Flight::getDepartureDate));
            return new Response("Flights retrieved successfully", Status.OK, flights);
        } catch (Exception e) {
            return new Response("Error retrieving flights list: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }
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
}
