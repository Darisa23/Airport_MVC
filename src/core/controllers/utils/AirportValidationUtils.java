/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers.utils;

import core.models.Location;
import java.util.List;

/**
 *
 * @author maria
 */
public class AirportValidationUtils {
    
    public static boolean isAirportIdUnique(String airportId, List<Location> existingLocations) {
        if (existingLocations == null) return true;
        return existingLocations.stream().noneMatch(loc -> loc.getAirportId().equals(airportId));
    }

    public static boolean isValidLatitude(double latitude) {
        return latitude >= -90.0 && latitude <= 90.0;
    }

    public static boolean isValidLongitude(double longitude) {
        return longitude >= -180.0 && longitude <= 180.0;
    }
    
    // Método de validación completo para un aeropuerto (ejemplo conceptual)
    public static boolean isAirportDataValid(String id, String latStr, String lonStr,
                                             List<Location> existingLocations, String... otherNonEmptyFields) {
        if (!FormatValidationUtils.isValidAirportIdFormat(id)) return false;
        if (!isAirportIdUnique(id, existingLocations)) return false;
        
        try {
            double latitude = Double.parseDouble(latStr);
            double longitude = Double.parseDouble(lonStr);
            if (!isValidLatitude(latitude)) return false;
            if (!FormatValidationUtils.hasMaxDecimalPlaces(latStr, 4)) return false;
            if (!isValidLongitude(longitude)) return false;
            if (!FormatValidationUtils.hasMaxDecimalPlaces(lonStr, 4)) return false;
        } catch (NumberFormatException e) {
            return false; // Latitud o longitud no son números válidos
        }
        
        if (GeneralValidationUtils.areAnyFieldsEmpty(otherNonEmptyFields)) return false;
        return true;
    }
}
