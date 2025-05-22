/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.DateUtils;
import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.controllers.utils.ValidationUtils;
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

    public Response createFlight(String id, String plane, String departureLocation, String arrivalLocation, String scaleLocation,
                                 String year, String month, String day, String hour, String minutes,
                                 String hoursDurationArrival, String minutesDurationArrival,
                                 String hoursDurationScale, String minutesDurationScale) {
        try {
            // 1. No empty fields
            if (ValidationUtils.anyEmpty(id, plane, departureLocation, arrivalLocation, year, hoursDurationArrival, minutesDurationArrival)) {
                return new Response("Fields cannot be empty", Status.BAD_REQUEST);
            }
            // 2. ID format validation
            if (!ValidationUtils.validId(id, 3, 3)) {
                return new Response("Invalid flight ID format. Must match XXXYYY (e.g., ABC123)", Status.BAD_REQUEST);
            }
            // 3. Check flight ID uniqueness
            if (StorageFlights.getInstance().get(id) != null) {
                return new Response("There is already a flight with that ID.", Status.BAD_REQUEST);
            }
            // 4. Plane existence validation
            Response planeRes = PlaneController.getPlane(plane);
            if (planeRes.getStatus() == Status.NOT_FOUND) {
                return new Response("The Selected Airplane does not exist", Status.BAD_REQUEST);
            }
            // 5. Departure location validation
            Response deLocRes = LocationController.getAirport(departureLocation);
            if (deLocRes.getStatus() == Status.NOT_FOUND) {
                return new Response("The Selected Departure_Location does not exist", Status.BAD_REQUEST);
            }
            // 6. Arrival location validation
            Response arrLocRes = LocationController.getAirport(arrivalLocation);
            if (arrLocRes.getStatus() == Status.NOT_FOUND) {
                return new Response("The Selected Arrival_Location does not exist", Status.BAD_REQUEST);
            }
            // 7. Departure date validation
            if (!DateUtils.isValidDate(year, month, day, hour, minutes, false)) {
                return new Response("Invalid Departure_Date", Status.BAD_REQUEST);
            }
            // 8. Scale location and duration validation
            Response scaleLocRes = null;
            int scaleHours = 0;
            int scaleMinutes = 0;
            //cuando si hay escala:
            if (scaleLocation != null && !scaleLocation.trim().isEmpty()) {
                scaleLocRes = LocationController.getAirport(scaleLocation);
                if (scaleLocRes.getStatus() == Status.NOT_FOUND) {
                    return new Response("The Selected Scale_Location does not exist", Status.BAD_REQUEST);
                }
                //ASI????*******************************
                scaleHours = Integer.parseInt(hoursDurationScale);
                scaleMinutes = Integer.parseInt(minutesDurationScale);
                if (scaleHours == 0 && scaleMinutes == 0) {
                    return new Response("Scale duration must be greater than 00:00 if Scale_Location is present", Status.BAD_REQUEST);
                }
            } else {//cuando no hay escala
                scaleHours = Integer.parseInt(hoursDurationScale);
                scaleMinutes = Integer.parseInt(minutesDurationScale);
                if (scaleHours != 0 || scaleMinutes != 0) {
                    return new Response("Scale duration must be 00:00 if Scale_Location is not present", Status.BAD_REQUEST);
                }
            }
            // 9. Validate total flight duration > 00:00
            int hoursDurArr = Integer.parseInt(hoursDurationArrival);
            int minutesDurArr = Integer.parseInt(minutesDurationArrival);
            if (!DateUtils.isTimeGreaterThanZero(hoursDurArr + scaleHours, minutesDurArr + scaleMinutes)) {
                return new Response("Flight duration must be greater than 00:00", Status.BAD_REQUEST);
            }
            // 10. Build departure date object
            
            if (!DateUtils.isValidDate(year, month, day, hour, minutes, false)) {
                return new Response("Invalid departure date", Status.BAD_REQUEST);
            }
            LocalDateTime departureDate = DateUtils.buildDate(year, month, day, hour, minutes);
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
            StorageFlights.getInstance().add(flight);
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
            return new Response("Flight retrieved successfully", Status.OK, flight);
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
}
