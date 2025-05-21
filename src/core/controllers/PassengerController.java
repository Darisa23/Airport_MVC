/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Passenger;
import core.models.storage.StoragePassengers;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author maria
 */


public class PassengerController {

    public static Response createPassenger(long id, String firstName, String lastName, LocalDate birthDate, 
            int countryPhoneCode, long phone, String country) {
        
        try {
            if (StoragePassengers.getInstance().get(id) != null) {
                return new Response("Passenger ID already exists", Status.BAD_REQUEST);
            }

            Passenger passenger = new Passenger(
                id,
                firstName,
                lastName,
                birthDate,
                countryPhoneCode,
                phone,
                country
            );

            StoragePassengers.getInstance().add(passenger);
            return new Response("Passenger created successfully", Status.CREATED, passenger);

        } catch (Exception e) {
            return new Response("Error creating passenger: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

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
