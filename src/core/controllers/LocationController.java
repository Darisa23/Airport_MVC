/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Location;
import core.models.storage.StorageLocations;
import core.services.LocationService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Alexander Sanguino
 */
public class LocationController {
 
    private final LocationService locationService;

    public LocationController() {
        this.locationService = new LocationService();
    }

    public Response createAirport(String id, String name, String city, String country, String latitude, String longitude) {
        return locationService.createAirport(id, name, city, country, latitude, longitude);
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
//obtener todos los id de aeropuertos:
    public Response getAllLocationIds() {
    ArrayList<Location> planes = StorageLocations.getInstance().getAll();
    List<String> ids = planes.stream()
                                 .map(p -> String.valueOf(p.getAirportId()))
                                 .toList(); 
    return new Response("Plane IDs retrieved", Status.OK, ids);
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