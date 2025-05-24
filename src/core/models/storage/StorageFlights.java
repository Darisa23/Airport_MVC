/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

import core.models.Flight;
import core.models.Observers.Observable;
import core.models.Observers.Observer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alexander Sanguino
 */
//owo
public class StorageFlights implements Storage<Flight, String>, Observable {

    private static StorageFlights instance;
    private ArrayList<Flight> flights;
    private final List<Observer> observers = new ArrayList<>();

    private StorageFlights() {
        this.flights = new ArrayList<>();
    }

    public static StorageFlights getInstance() {
        if (instance == null) {
            instance = new StorageFlights();
        }
        return instance;
    }

    @Override
    public boolean add(Flight flight) {
        Flight fl = this.get(flight.getId()); //check if it existed already
        if (fl != null) {
            return false;
        }
        //Lo agregamos si no está
        flights.add(flight);
        notifyObservers();
        return true;
    }

    @Override
  public boolean delete(Flight flight) {
    Flight fl = this.get(flight.getId());
    if (fl != null) {
        boolean removed = flights.remove(fl);
        if (removed) {
            notifyObservers(); // ✅ Notificamos si efectivamente se eliminó
        }
        return removed;
    }
    return false;
}


    @Override
    public boolean update(Flight flight) {
        // First, check if the flight exists in the storage
        Flight existingFlight = this.get(flight.getId());
        if (existingFlight != null) {
            // If it exists, remove the old one and add the new one
            // This effectively "updates" the flight by replacing it
            flights.remove(existingFlight);
            flights.add(flight);
            notifyObservers(); // Notificamos después de actualizar
            return true;
        }
        return false; // Flight not found, so it cannot be updated
    }

    @Override
    public Flight get(String id) {
        for (Flight fl : flights) {
            if (fl.getId().equals(id)) {
                return fl;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Flight> getAll() {
        return new ArrayList<>(flights);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update();
        }
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

}
