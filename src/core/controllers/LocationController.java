/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.controllers.utils.validators.LocationValidator;
import core.controllers.utils.validators.ValidationUtils;
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
        try {
            // 1. Validaciones de presencia (campos vacíos)
            if (ValidationUtils.anyEmpty(id, name, city, country, latitude, longitude)) {
                return new Response("Fields cannot be empty", Status.BAD_REQUEST);
            }

            // 2. Validaciones de formato y otras reglas específicas (con LocationValidator)
            Response validationResponse = LocationValidator.INSTANCE.isValid(id, name, city, country, latitude, longitude);
            if (validationResponse.getStatus() != Status.OK) {
                return validationResponse;
            }

            // 3. Parseo de Strings a Double (latitud, longitud).
            double parsedLatitude;
            double parsedLongitude;
            try {
                parsedLatitude = Double.parseDouble(latitude);
                parsedLongitude = Double.parseDouble(longitude);
            } catch (NumberFormatException e) {
                return new Response("Invalid number format for latitude or longitude.", Status.BAD_REQUEST);
            }

            // 4. Validación de negocio: Verificar si ya existe la ubicación (usando el servicio para consultar).
            if (locationService.getLocation(id) != null) {
                return new Response("There is already a location with that ID.", Status.BAD_REQUEST);
            }

            // 5. Si todas las validaciones pasaron, llamar al servicio.
            Location newLocation = locationService.registerLocation(id, name, city, country, parsedLatitude, parsedLongitude);

            return new Response("Airport created", Status.CREATED, newLocation);

        } catch (IllegalStateException e) {
            return new Response("Internal Server Error during location creation: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new Response("An unexpected error occurred: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response getAirport(String id) {
        try {
            Location passenger = StorageLocations.getInstance().get(id);
            if (passenger == null) {
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
