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
import core.models.Observers.Observer;
import core.models.Passenger;
import core.models.storage.StoragePassengers;
import core.services.PassengerService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author maria
 */
public class PassengerController {
       private final PassengerService passengerService;
        public PassengerController() {
        this.passengerService = new PassengerService();
    }
       public Response registerPassenger(String id, String firstName, String lastName, String year, String month, String day,
                                      String countryPhoneCode, String phone, String country) {
        return passengerService.registerPassenger(id, firstName, lastName, year, month, day, countryPhoneCode, phone, country);
    }

    //CAMBIÉ ESTO A QUE RECIBE STRING QUIZÁ HAGA PROBLEMASSSS*******************************
    public Response getPassenger(String id) {
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
//obtener todos los id de pasajeros:
    public Response getAllPassengerIds() {
    ArrayList<Passenger> passengers = StoragePassengers.getInstance().getAll();
    List<String> ids = passengers.stream()
                                 .map(p -> String.valueOf(p.getId()))
                                 .toList(); 
    return new Response("Passenger IDs retrieved", Status.OK, ids);
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
            if(id.isEmpty()){
            return new Response("Debe seleccionar un Id de usuario para actualizar su información", Status.NO_CONTENT);
        }
            Response passengerRes = getPassenger(id);

            if (passengerRes.getStatus() == Status.NOT_FOUND) {
                return passengerRes;
            }
            if (ValidationUtils.anyEmpty(id, newFirstName,newLastName,newYear, newMonth, newDay, newCountryCode, newPhone, newCountry)) {
            return new Response("Fields cannot be empty", Status.BAD_REQUEST);
        }
            Response Invalid = PassengerValidator.INSTANCE.isValid(id, newFirstName, newLastName, newYear, newMonth,
                    newDay, newCountryCode, newPhone, newCountry);
            if (Invalid.getStatus() != Status.OK) {
                return Invalid;
            }
            Passenger updatedPassenger = new Passenger(
                    Long.parseLong(id),
                    newFirstName,
                    newLastName,
                    DateUtils.buildDate(newYear, newMonth, newDay),
                    Integer.parseInt(newCountryCode),
                    Long.parseLong(newPhone),
                    newCountry
            );
            StoragePassengers.getInstance().update(updatedPassenger);
            return new Response("Passenger updated successfully", Status.OK);

        } catch (Exception e) {
            return new Response("Error updating passenger: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    public Response addToFlight(String passengerId, Flight flight) {
        try {
            Response opassenger = getPassenger(passengerId);
            if (opassenger.getStatus() == Status.NOT_FOUND) {
                return opassenger;
            }
            Passenger passenger = StoragePassengers.getInstance().get(Long.valueOf(passengerId));
            System.out.println("luego este");
            boolean added = passenger.addFlight(flight);
            if (!added) {
                return new Response("Passenger is already on this flight", Status.BAD_REQUEST);
            }
            return new Response("Flight added to passenger successfully", Status.OK,passenger);
        } catch (Exception e) {
            return new Response("Error adding flight to passenger: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }
    public Response getFlightsOfPassenger(String passengerId) {
        if(passengerId.equals("Select User")){
            return new Response("Debe seleccionar un Id de usuario para ver sus vuelos", Status.NO_CONTENT);
        }
    Passenger passenger = StoragePassengers.getInstance().get(Long.valueOf(passengerId));
    if (passenger == null) {
        return new Response("Passenger not found", Status.NOT_FOUND);
    }
    if(passenger.getFlights().isEmpty()){
        return new Response("El usuario "+passengerId+" no tiene ningún vuelo registrado", Status.NOT_FOUND);
    }
    return new Response("Flights retrieved", Status.OK, passenger.getFlights());
}
    public void registerObserver(Observer observer) {
    StoragePassengers.getInstance().addObserver(observer);
}
    public Response getPassengerData(String id) {
    Passenger passenger = StoragePassengers.getInstance().get(Long.valueOf(id));
    if (passenger == null) {
        return new Response("Passenger not found", Status.NOT_FOUND);
    }

    Map<String, String> data = new HashMap<>();
    data.put("id", String.valueOf(passenger.getId()));
    data.put("firstName", passenger.getFirstname());
    data.put("lastName", passenger.getLastname());
    data.put("birthYear", String.valueOf(passenger.getBirthDate().getYear()));
    data.put("phoneCode", String.valueOf(passenger.getCountryPhoneCode()));
    data.put("phone", String.valueOf(passenger.getPhone()));
    data.put("country", passenger.getCountry());

    return new Response("Data ready", Status.OK, data);
}
    public Response getFlightRowsOfPassenger(String id) {
        //acá por ejemplo en vez de acceder al storage le pide al service que revise si existe
        //**********************************************************************
    Passenger passenger = StoragePassengers.getInstance().get(Long.valueOf(id));
    if (passenger == null) {
        return new Response("Passenger not found", Status.NOT_FOUND);
    }
    //ESTA TAMBIEN IRÍA AL SERVICE
    ArrayList<Flight> flights = passenger.getFlights();
    List<Object[]> rows = new ArrayList<>();

    for (Flight f : flights) {
        Object[] row = new Object[] {
            f.getId(),
            f.getDepartureDate(),
            f.calculateArrivalDate()
        };
        rows.add(row);
    }

    return new Response("Flights refreshed", Status.OK, rows);
}
    
public Response getPassengers() {
    
    List<Object[]> rows = new ArrayList<>();

    for (Passenger p : StoragePassengers.getInstance().getAll()) {
        Object[] row = new Object[] {
            p.getId(), 
            p.getFullname(), 
            p.getBirthDate(), 
            p.calculateAge(), 
            p.generateFullPhone(), 
            p.getCountry(), 
            p.getNumFlights()
        };
        rows.add(row);
    }

    return new Response("Passengers refreshed", Status.OK, rows);
}
}
