/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers.utils.validators;

import core.controllers.LocationController;
import core.controllers.PlaneController;
import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.storage.StorageFlights;
import core.models.storage.StoragePassengers;
import java.time.LocalDateTime;

/**
 *
 * @author maria
 */
public class FlightValidator implements Validator {

    public static final FlightValidator INSTANCE = new FlightValidator();

    private FlightValidator() {
    }

    @Override
    public Response isValid(String... fields) {

        String id = fields[0];
        String plane = fields[1];
        String departureLocation = fields[2];
        String arrivalLocation = fields[3];
        String scaleLocation = fields[4];
        String year = fields[5];
        String month = fields[6];
        String day = fields[7];
        String hour = fields[8];
        String minutes = fields[9];
        String hoursDurationArrival = fields[10];
        String minutesDurationArrival = fields[11];
        String hoursDurationScale = fields[12];
        String minutesDurationScale = fields[13];

        // 1. No empty fields
        if (ValidationUtils.anyEmpty(id, plane, departureLocation, arrivalLocation, year, hour, day, hoursDurationArrival, minutesDurationArrival)) {
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

        // 6.1. Validate distinct locations
        if (departureLocation.equals(arrivalLocation)) {
            return new Response("Departure and Arrival locations must be different", Status.BAD_REQUEST);
        }
        // 7. Departure date validation
        if (!DateUtils.isValidDate(year, month, day, hour, minutes, false)) {
            return new Response("Invalid Departure_Date", Status.BAD_REQUEST);
        }
        if (!scaleLocation.equals("Location")) {
            if (departureLocation.equals(scaleLocation)) {
                return new Response("Departure and Scale locations must be different", Status.BAD_REQUEST);
            }
            if (arrivalLocation.equals(scaleLocation)) {
                return new Response("Arrival and Scale locations must be different", Status.BAD_REQUEST);
            }
        }
         try {
            Integer.parseInt(hoursDurationArrival);
            Integer.parseInt(minutesDurationArrival);
        } catch (NumberFormatException e) {
            return new Response("Please select a valid arrival duration", Status.BAD_REQUEST);
        }
        // 8. Scale location and duration validation
        int scaleHours;
        int scaleMinutes;
        try {
            scaleHours = Integer.parseInt(hoursDurationScale);
            scaleMinutes = Integer.parseInt(minutesDurationScale);
        } catch (NumberFormatException e) {
            return new Response("Scale duration must be numeric", Status.BAD_REQUEST);
        }

        if (scaleLocation.equals("Location")) {//when there´s not a scale
            if (scaleHours != 0 && scaleMinutes != 0) {
                return new Response("Scale duration must be 00:00 if Scale_Location is not present", Status.BAD_REQUEST);
            }
        } else { //when there's a scale:
            Response scaleLocRes = LocationController.getAirport(scaleLocation);
            if (scaleLocRes.getStatus() == Status.NOT_FOUND) {
                return new Response("The Selected Scale_Location does not exist", Status.BAD_REQUEST);
            }

            if (scaleHours == 0 && scaleMinutes == 0) {
                return new Response("Scale duration must be greater than 00:00 if Scale_Location is present", Status.BAD_REQUEST);
            }
            // 9. Validate total flight duration > 00:00
            if (!DateUtils.isValidDuration(hoursDurationArrival, minutesDurationArrival, hoursDurationScale, minutesDurationScale)) {
                return new Response("Flight duration must be greater than 00:00", Status.BAD_REQUEST);
            }
        }
        // 10. Build departure date object

        if (!DateUtils.isValidDate(year, month, day, hour, minutes, false)) {
            return new Response("Invalid departure date", Status.BAD_REQUEST);
        }

        return new Response("Valid Flight", Status.OK);
    }
}
