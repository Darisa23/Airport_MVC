/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

import core.models.Location;
import core.models.Observers.Observable;
import core.models.Observers.Observer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alexander Sanguino
 */
public class StorageLocations implements Storage<Location, String>, Observable {

    private static StorageLocations instance;
    private final ArrayList<Location> airports;
    private final List<Observer> observers = new ArrayList<>();

    private StorageLocations() {
        this.airports = new ArrayList<>();
    }

    public static StorageLocations getInstance() {
        if (instance == null) {
            instance = new StorageLocations();
        }
        return instance;
    }

    @Override
    public boolean add(Location location) {
        Location loc = this.get(location.getAirportId()); //revisamos si ya estaba:
        if (loc != null) {
            return false;
        }
        //Lo agregamos si no está
        this.airports.add(location);
        notifyObservers();
        return true;
    }

    @Override
    public boolean delete(Location location) {
        Location l1 = this.get(location.getAirportId()); // Revisamos si ya estaba
        if (l1 != null) {
            boolean removed = this.airports.remove(l1); // Eliminamos del ArrayList
            if (removed) {
                notifyObservers(); // Notificamos si se eliminó 
            }
            return removed;
        }
        return false;
    }

    @Override
    public boolean update(Location location) { 
     
        Location existingLocation = this.get(location.getAirportId());
        if (existingLocation != null) {
            this.airports.remove(existingLocation);
            this.airports.add(location);
            notifyObservers();
            return true;
        }
        return false; 
    }

    @Override
    public Location get(String id) {
        for (Location loc : airports) {
            if (loc.getAirportId().equals(id)) {
                return loc;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Location> getAll() {
        return new ArrayList<>(airports);
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
