/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.controllers.utils.ValidationUtils;
import core.models.Location;
import core.models.Passenger;
import core.models.storage.StorageLocations;
import core.models.storage.StoragePassengers;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Alexander Sanguino
 */
public class LocationController {
    
    private final StorageLocations locationStorage;

    public LocationController() {
        this.locationStorage  = StorageLocations.getInstance();
    }
    
    public Response createAirport(String id, String name, String city, String country, String latitude, String longitude) {
        try {
            //1. No empty fields
            if (ValidationUtils.anyEmpty(id, name, city, country,latitude,longitude)) {
                return new Response("Fields cannot be empty", Status.BAD_REQUEST);
            }
            //2. ID Validation
            if (id.matches("[A-Z]{3}")) {
                return new Response("Airport Id must be 3 Uppercase letters", Status.BAD_REQUEST);
            }
            if (StorageLocations.getInstance().get(id) != null) {
                return new Response("There is already an airport with that ID.", Status.BAD_REQUEST);
            }
            //3. Check latitude and longitude
            if (ValidationUtils.validNum(-90, 90, latitude, 4)){
                return new Response("Airport latitude must be a number between -90 and 90.", Status.BAD_REQUEST);
            }
            if (ValidationUtils.validNum(-180, 180, longitude, 4)) {
                return new Response("Airport longitude must be a number between -180 and 180.", Status.BAD_REQUEST);
            }
            //Add to storage
            Location location = new Location(id, name, city, country, Double.parseDouble(latitude), Double.parseDouble(longitude));
            StorageLocations.getInstance().add(location);
            // All Good :D
            return new Response("Airport created", Status.CREATED);
        } catch (Exception e) {
            return new Response("Internal Server Error", Status.INTERNAL_SERVER_ERROR);
        }

    }
    
    public static Response getAirport(String id) {
        try {
            Location passenger = StorageLocations.getInstance().get(id);
            if (passenger== null) {
                return new Response("Airport not found", Status.NOT_FOUND);
            }
            return new Response("Airport retrieved successfully", Status.OK, passenger);

        } catch (Exception e) {
            return new Response("Error retrieving airports: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    public Response getAllAirports() {
        try {
            ArrayList<Location> locations = StorageLocations.getInstance().getAll();
            Collections.sort(locations, Comparator.comparing(Location::getAirportId));
            return new Response("Airports retrieved successfully", Status.OK, locations);

        } catch (Exception e) {
            return new Response("Error retrieving airports list: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
}
    public static Response addLocation(Location location) {
        try {
            boolean added = StorageLocations.getInstance().add(location);
            if (!added) {
                return new Response("This location already exists", Status.BAD_REQUEST);
            }
            return new Response("Location added successfully", Status.OK);

        } catch (Exception e) {
            return new Response("Error retrieving location: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }
}