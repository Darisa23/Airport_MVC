/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.services;

import core.controllers.utils.validators.ValidationUtils;
import core.models.Location;
import core.models.Observers.Observer;
import core.models.storage.StorageLocations;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author maria
 */
public class LocationService {

    public Location registerLocation(String id, String name, String city, String country, double latitude, double longitude) {
        Location location = new Location(id, name, city, country, latitude, longitude);

        boolean added = StorageLocations.getInstance().add(location);
        if (!added) {
            throw new IllegalStateException("Failed to add location to storage. Controller should have checked for existence.");
        }
        return location;
    }

    public Location getLocation(String id) {
       
        return StorageLocations.getInstance().get(id);
    }

    public List<String> allLocations() {
           List<String> ids = StorageLocations.getInstance()
                .getAll().stream()
                .map(Location::getAirportId)
                .toList();

        // Para formato "AAA" (3 letras)
        return ValidationUtils.sortList(ids, 3, 0);
    }

    public void addObserver(Observer observer) {
        StorageLocations.getInstance().addObserver(observer);
    }

    public ArrayList<Object[]> completeInfo() {
        ArrayList<Object[]> rows = new ArrayList<>();
        for (Location l : StorageLocations.getInstance().getAll()) {
            Object[] row = new Object[]{
                l.getAirportId(),
                l.getAirportName(),
                l.getAirportCity(),
                l.getAirportCountry()
            };
            rows.add(row);
        }

        return rows;
    }

}
