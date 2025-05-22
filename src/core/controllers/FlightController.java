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
import core.models.storage.StorageFlights;
import core.models.storage.StorageLocations;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Alexander Sanguino
 */
public class FlightController {
     public Response createFlight(String id, String plane, String departureLocation, String arrivalLocation,String scaleLocation, 
             String year, String month, String day,String hour,String minutes, String hoursDurationArrival, String minutesDurationArrival, String hoursDurationScale, String minutesDurationScale) {   
        try {
            //Validaciones:
            //1. No empty fields
            if (ValidationUtils.anyEmpty(id,plane,departureLocation,arrivalLocation,year,hoursDurationArrival,minutesDurationArrival)){
                return new Response("Fields cannot be empty", Status.BAD_REQUEST);
            }
            // 2. ID Validation
            if (!ValidationUtils.validId(id,3,3)) {
                return new Response("Invalid flight ID format. Must match XXXYYY (e.g., ABC123)", Status.BAD_REQUEST);
            }
            //**************************************************************************
            //ESTO SE CAMBIA POR LO QUE TE DIJE DE LOS GETTERS QUE HACEN EL TRABAJO DE COMUNICARSE CON EL STORAGE:
            //ASÍ PARA REVISAR SI EXISTE EL AVIÓN Y LAS LOCATION SE ACCEDERÁ A LOS GET CORRESPONDIENTES DE LOS CONTROLADORES
            //*************************************************************************************
            if (StorageFlights.getInstance().get(id) != null) {
                return new Response("There is already a flight with that ID.", Status.BAD_REQUEST);
            }
            // 3. Plane Validation
            Response planeRes = PlaneController.getPlane(plane);
            if (planeRes.getStatus()== Status.NOT_FOUND) {
                return new Response("The Selected Airplane does not exist", Status.BAD_REQUEST);
            }
            // 4. DepartureLocation Validation CAMBIAR COMO EL DE PLANEVALIDATION  QUE USA EL CONTROLLER**********
            Location loca = StorageLocations.getInstance().get(departureLocation);
            if (loca==null) {
                return new Response("The Selected Departure_Location does not exist", Status.BAD_REQUEST);
            }
            // 5. ArrivalLocation Validation CAMBIAR COMO EL DE PLANEVALIDATION  QUE USA EL CONTROLLER**********
            Location locb =StorageLocations.getInstance().get(arrivalLocation);
            if (locb==null) {
                return new Response("The Selected Arrival_Location does not exist", Status.BAD_REQUEST);
            }
            //7. Valid Date:
            if (!DateUtils.isValidDate(year, month, day, hour, minutes, false)){
                return new Response("The Selected Departure_Date does not exist", Status.BAD_REQUEST);
            }
            // 6. Scale or not:
            if (scaleLocation!=null){
                Location locc =StorageLocations.getInstance().get(arrivalLocation);
            if (locc==null) {
                return new Response("The Selected Scale_Location does not exist", Status.BAD_REQUEST);
            }
            //continuar...
            }
            Flight flight = new flight(
                    Long.parseLong(id),
                    planeRes.getObject(),
                    loca,
                    locb,
                    DateUtils.buildDate(year, month, day),
                    Integer.parseInt(countryPhoneCode),
                    Long.parseLong(phone),
                    country
            );

           StorageFlights.getInstance().add(flight);
           return new Response("Passenger created successfully", Status.CREATED, flight);
           } catch (Exception e) {
            return new Response("Error Registering passenger: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
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
            return new Response("Error retrieving plane: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response getAllFlight() {
        try {
            ArrayList<Flight> flights = StorageFlights.getInstance().getAll();
            Collections.sort(flights, Comparator.comparing(Flight::getId));
            return new Response("Planes retrieved successfully", Status.OK, flights);

        } catch (Exception e) {
            return new Response("Error retrieving planes list: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
     }
}

