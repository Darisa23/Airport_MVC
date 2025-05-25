/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.services;

import core.models.Location;
import core.models.storage.StorageLocations;

/**
 *
 * @author maria
 */
public class LocationService {

    // registerLocation ahora recibe los tipos de datos ya parseados y validados por el controlador.
    public Location registerLocation(String id, String name, String city, String country, double latitude, double longitude) {
        Location location = new Location(id, name, city, country, latitude, longitude);

        boolean added = StorageLocations.getInstance().add(location);
        if (!added) {
            throw new IllegalStateException("Failed to add location to storage. Controller should have checked for existence.");
        }
        return location;
    }

    // getLocation simplemente recupera, sin validaciones de entrada.
    public Location getLocation(String id) {
        return StorageLocations.getInstance().get(id);
    }
}