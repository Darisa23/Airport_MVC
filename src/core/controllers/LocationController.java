/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Location;
import core.models.storage.StorageLocations;

/**
 *
 * @author Alexander Sanguino
 */
public class LocationController {

    public static Response createAirport(String id, String name, String city, String country, String latitude, String longitude) {
        try {
            //Check empty fields
            if (id.trim().isEmpty()) {
                return new Response("Airport Id can't be empty", Status.BAD_REQUEST);
            }
            if (name.trim().isEmpty()) {
                return new Response("Airport name can't be empty", Status.BAD_REQUEST);
            }
            if (city.trim().isEmpty()) {
                return new Response("Airport city can't be empty", Status.BAD_REQUEST);
            }
            if (country.trim().isEmpty()) {
                return new Response("Airport country can't be empty", Status.BAD_REQUEST);
            }
            if (latitude.trim().isEmpty()) {
                return new Response("Airport latitude can't be empty", Status.BAD_REQUEST);
            }
            if (longitude.trim().isEmpty()) {
                return new Response("Airport longitude can't be empty", Status.BAD_REQUEST);
            }

            //Check id: 3 letters in all caps
            if (id.length() != 3) {
                return new Response("Airport Id must be 3 characters long", Status.BAD_REQUEST);
            }
            /*if (!Verifying.checkAirportId(id)) {
                return new Response("Airport Id must have Uppercase letters only.", Status.BAD_REQUEST);
            }*/

            //Check latitude and longitude type
            double dLatitude, dLongitude;
            try {
                dLatitude = Double.parseDouble(latitude);
            } catch (NumberFormatException ex) {
                return new Response("Airport latitude must be a number.", Status.BAD_REQUEST);
            }
            try {
                dLongitude = Double.parseDouble(longitude);
            } catch (NumberFormatException ex) {
                return new Response("Airport longitude must be a number.", Status.BAD_REQUEST);
            }

            //Check latitude and longitude interval
            if (dLatitude > 90 | dLatitude < -90) {
                return new Response("Airport latitude must be between -90 and 90.", Status.BAD_REQUEST);
            }
            if (dLongitude > 180 | dLongitude < -180) {
                return new Response("Airport longitude must be between -180 and 180.", Status.BAD_REQUEST);
            }

            //Check latitude and longitude decimals
            String[] decimals = latitude.split(",", 2);
            if (decimals[1].length() > 4) {
                return new Response("Airport latitude must have 4 or less decimals", Status.BAD_REQUEST);
            }
            decimals = longitude.split(",", 2);
            if (decimals[1].length() > 4) {
                return new Response("Airport longitude must have 4 or less decimals", Status.BAD_REQUEST);
            }
            //Add to storage
            if(!addLocation(new Location(id, name, city, country, dLatitude, dLongitude))){
                 return new Response("There's already an airport with that Id", Status.BAD_REQUEST);
            }
            // All Good :D
            return new Response("Airport created", Status.CREATED);
        } catch (Exception e) {
            return new Response("Internal Server Error", Status.INTERNAL_SERVER_ERROR);
        }

    }

    public static boolean addLocation(Location airport) {
        StorageLocations locationStorage = StorageLocations.getInstance();
        return locationStorage.add(airport);
    }
}
