/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.controllers.utils.Validation;
import core.models.Passenger;
import core.models.storage.StoragePassengers;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author maria
 */
public class PassengerController {

    public Response registerPassenger(String id, String firstName, String lastName, String year,String month,String day,
            String countryPhoneCode, String phone, String country) {   
        try {
            //Validaciones:
            //1. No empty fields
            if (Validation.anyEmpty(id, firstName, lastName, year,month,day, countryPhoneCode, phone, country)) {
                return new Response("Fields cannot be empty", Status.BAD_REQUEST);
            }
            // 2. ID Validation
            if (!Validation.isNumericWithDigitRange(id, 1, 15)) {
                return new Response("Invalid passenger ID. It must be numeric, not empty and have at least 15 digits.", Status.BAD_REQUEST);
            }
            //QUIZAS PROBLEMAS PROQUE ES MEJOR PARSEAR:******************************
            if (StoragePassengers.getInstance().get(Long.valueOf(id)) != null) {
                return new Response("There is already a passenger with that ID.", Status.BAD_REQUEST);
            }
            // 3. CountryPhoneCode Validation
            if (!Validation.isNumericWithDigitRange(countryPhoneCode, 1, 3)) {
                return new Response("Invalid passenger CountryPhoneCode. It must be numeric, not empty and have at least 3 digits.", Status.BAD_REQUEST);
            }
            // 4. Phone Validation
            if (!Validation.isNumericWithDigitRange(phone, 1, 11)) {
                return new Response("Invalid passenger Phone. It must be numeric, not empty and have at least 3 digits.", Status.BAD_REQUEST);
            }
            // 5. BirthDate Validation
            if (!Validation.isValidDate(year, month, day)){
                return new Response("Invalid BirthDate.", Status.BAD_REQUEST);
            }
            Passenger passenger = new Passenger(
                    Long.parseLong(id),
                    firstName,
                    lastName,
                    Validation.buildDate(year, month, day),
                    Integer.parseInt(countryPhoneCode),
                    Long.parseLong(phone),
                    country
            );

           StoragePassengers.getInstance().add(passenger);
           return new Response("Passenger created successfully", Status.CREATED, passenger);
           } catch (Exception e) {
            return new Response("Error Registering passenger: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }
//ESTOS REVISAR Y ADECUAR: *******************************************************************
    public static Response updatePassenger(long id, String newFirstName, String newLastName,
            LocalDate newBirthDate, int newCountryCode, long newPhone, String newCountry) {

        try {
            Passenger passenger = StoragePassengers.getInstance().get(id);

            if (passenger == null) {
                return new Response("Passenger not found", Status.NOT_FOUND);
            }

            passenger.setFirstname(newFirstName);
            passenger.setLastname(newLastName);
            passenger.setBirthDate(newBirthDate);
            passenger.setCountryPhoneCode(newCountryCode);
            passenger.setPhone(newPhone);
            passenger.setCountry(newCountry);

            return new Response("Passenger updated successfully", Status.OK, passenger);

        } catch (Exception e) {
            return new Response("Error updating passenger: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response addToFlight(long passengerId, String flightId) {
        try {
            Passenger passenger = StoragePassengers.getInstance().get(passengerId);

            if (passenger == null /*|| flight == null*/) {
                return new Response("Passenger or flight not found", Status.NOT_FOUND);
            }

            // Lógica pendiente: agregar a vuelo
            return new Response("Added to flight successfully", Status.OK, passenger);

        } catch (Exception e) {
            return new Response("Error adding to flight: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response getAllPassengers() {
        try {
            ArrayList<Passenger> passengers = StoragePassengers.getInstance().getAll();
            return new Response("Passenger list retrieved", Status.OK, passengers);
        } catch (Exception e) {
            return new Response("Error retrieving passengers: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response getPassenger(long id) {
        try {
            Passenger passenger = StoragePassengers.getInstance().get(id);
            if (passenger == null) {
                return new Response("Passenger not found", Status.NOT_FOUND);
            }
            return new Response("Passenger found", Status.OK, passenger);
        } catch (Exception e) {
            return new Response("Error retrieving passenger: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }
}
