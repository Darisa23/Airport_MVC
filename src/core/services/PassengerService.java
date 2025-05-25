/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.services;

import core.models.Flight;
import core.models.Observers.Observer;
import core.models.Passenger;
import core.models.storage.StoragePassengers;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author maria
 */
public class PassengerService {

    public Passenger registerPassenger(long id, String firstName, String lastName, LocalDate Birthday,
            int countryPhoneCode, long phone, String country) {

        Passenger passenger = new Passenger(
                id,
                firstName,
                lastName,
                Birthday,
                countryPhoneCode,
                phone,
                country
        );
        boolean added = StoragePassengers.getInstance().add(passenger);
        if (!added) {
            throw new IllegalStateException("Failed to add passenger to storage despite prior checks by controller.");
        }

        return passenger;
    }

    public Passenger getPassenger(long id) {
        return StoragePassengers.getInstance().get(id);
    }

    public List<String> allPassengers() {
        List<String> ids = StoragePassengers.getInstance()
                .getAll().stream()
                .map(Passenger::getId)
                .sorted()
                .map(String::valueOf)
                .toList();
        return ids;
    }

    public boolean addAflight(long id, Flight flight) {
        return StoragePassengers.getInstance().get(id).addFlight(flight);
    }

    public void addObserver(Observer observer) {
        StoragePassengers.getInstance().addObserver(observer);
    }

    public void addObserver(String id, Observer observer) {
        StoragePassengers.getInstance().get(Long.valueOf(id)).addObserver(observer);
    }

    public void update(long id, String firstName, String lastName, LocalDate Birthday,
            int countryPhoneCode, long phone, String country) {
        Passenger updatedPassenger = registerPassenger(id, firstName, lastName, Birthday, countryPhoneCode, phone, country);

        StoragePassengers.getInstance().update(updatedPassenger);
    }

    public HashMap<String, String> specificData(long id) {
        HashMap<String, String> data = new HashMap<>();
        Passenger p = StoragePassengers.getInstance().get(id);
        data.put("id", String.valueOf(p.getId()));
        data.put("firstName", p.getFirstname());
        data.put("lastName", p.getLastname());
        data.put("birthYear", String.valueOf(p.getBirthDate().getYear()));

        data.put("phoneCode", String.valueOf(p.getCountryPhoneCode()));
        data.put("phone", String.valueOf(p.getPhone()));
        data.put("country", p.getCountry());
        return data;
    }

    public ArrayList<Object[]> flightsOf(long idPassenger) {
        List<Flight> flights = StoragePassengers.getInstance().get(idPassenger).getFlights();

        // Ordenar directamente por fecha de salida
        flights.sort(Comparator.comparing(Flight::getDepartureDate));

        ArrayList<Object[]> rows = new ArrayList<>();
        for (Flight f : flights) {
            Object[] row = new Object[]{
                f.getId(),
                f.getDepartureDate(),
                f.calculateArrivalDate()
            };
            rows.add(row);
        }
        return rows;
    }

    public ArrayList<Object[]> completeInfo() {
        ArrayList<Object[]> rows = new ArrayList<>();

        for (Passenger p : StoragePassengers.getInstance().getAll().stream()
                .sorted(Comparator.comparing(Passenger::getId))
                .collect(Collectors.toList())) {
            Object[] row = new Object[]{
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
        return rows;
    }

}
