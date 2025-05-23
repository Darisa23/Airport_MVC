/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.validators.DateUtils;
import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.controllers.utils.validators.PassengerValidator;
import core.controllers.utils.validators.ValidationUtils;
import core.models.Flight;
import core.models.Passenger;
import core.models.Plane;
import core.models.storage.StorageFlights;
import core.models.storage.StoragePassengers;
import core.models.storage.StoragePlanes;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author maria
 */
public class PassengerController {

    public Response registerPassenger(String id, String firstName, String lastName, String year, String month, String day,
            String countryPhoneCode, String phone, String country) {
        try {
            //Validaciones:
            Response Invalid = PassengerValidator.INSTANCE.isValid(id, firstName, lastName, year, month, day, countryPhoneCode, phone, country);
            if (Invalid.getStatus() != Status.OK) {
                return Invalid;
            }
            Passenger passenger = new Passenger(
                    Long.parseLong(id),
                    firstName,
                    lastName,
                    DateUtils.buildDate(year, month, day),
                    Integer.parseInt(countryPhoneCode),
                    Long.parseLong(phone),
                    country
            );

            addPassenger(passenger);
            return new Response("Passenger created successfully", Status.CREATED, passenger);
        } catch (Exception e) {
            return new Response("Error Registering passenger: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    //CAMBIÉ ESTO A QUE RECIBE STRING QUIZÁ HAGA PROBLEMASSSS*******************************
    public static Response getPassenger(String id) {
        try {
            long nid = Long.parseLong(id);
            Passenger passenger = StoragePassengers.getInstance().get(nid);
            if (passenger == null) {
                return new Response("Passenger not found", Status.NOT_FOUND);
            }
            return new Response("Passenger retrieved successfully", Status.OK, passenger);

        } catch (Exception e) {
            return new Response("Error retrieving passenger: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    public Response getAllPassengers() {
        try {
            ArrayList<Passenger> passengers = StoragePassengers.getInstance().getAll();
            Collections.sort(passengers, Comparator.comparing(Passenger::getId));
            return new Response("Planes retrieved successfully", Status.OK, passengers);

        } catch (Exception e) {
            return new Response("Error retrieving passengers list: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response addPassenger(Passenger passenger) {
        try {
            boolean added = StoragePassengers.getInstance().add(passenger);
            if (!added) {
                return new Response("This passenger already exists", Status.BAD_REQUEST);
            }
            return new Response("passenger added successfully", Status.OK);
        } catch (Exception e) {
            return new Response("Error retrieving passenger: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }


    public Response updatePassenger(String id, String newFirstName, String newLastName,
            String newYear, String newMonth, String newDay, String newCountryCode, String newPhone, String newCountry) {

        try {

            Response passengerRes = getPassenger(id);

            if (passengerRes.getStatus() == Status.NOT_FOUND) {
                return passengerRes;
            }
            Passenger passenger = (Passenger) passengerRes.getObject();
            Response Invalid = PassengerValidator.INSTANCE.isValid(id, newFirstName, newLastName, newYear, newMonth,
                    newDay, newCountryCode, newPhone, newCountry);
            if (Invalid.getStatus() != Status.OK) {
                return Invalid;
            }
            passenger.setFirstname(newFirstName);
            passenger.setLastname(newLastName);
            passenger.setBirthDate(DateUtils.buildDate(newYear, newMonth, newDay));
            passenger.setCountryPhoneCode(Integer.parseInt(newCountryCode));
            passenger.setPhone(Long.parseLong(newPhone));
            passenger.setCountry(newCountry);

            return new Response("Passenger updated successfully", Status.OK);

        } catch (Exception e) {
            return new Response("Error updating passenger: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response addToFlight(Passenger passenger, Flight flight) {
        try {
            boolean added = passenger.addFlight(flight);
            if (!added) {
                return new Response("Passenger is already on this flight", Status.BAD_REQUEST);
            }
            return new Response("Flight added to passenger successfully", Status.OK);
        } catch (Exception e) {
            return new Response("Error adding flight to passenger: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

}
