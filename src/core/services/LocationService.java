/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.services;

import core.controllers.LocationController;
import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.controllers.utils.validators.LocationValidator;
import core.models.Location;

/**
 *
 * @author maria
 */
public class LocationService {
    public Response createAirport(String id, String name, String city, String country, String latitude, String longitude) {
        try {
            Response invalid = LocationValidator.INSTANCE.isValid(id, name, city, country, latitude, longitude);
            if (invalid.getStatus() != Status.OK) {
                return invalid;
            }

            Location location = new Location(id, name, city, country, Double.parseDouble(latitude), Double.parseDouble(longitude));
            return LocationController.addLocation(location);

        } catch (Exception e) {
            return new Response("Internal Server Error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
