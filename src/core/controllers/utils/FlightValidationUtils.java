/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers.utils;

import core.models.Flight;
import core.models.Location;
import core.models.Plane;
import java.util.List;

/**
 *
 * @author maria
 */
public class FlightValidationUtils {
    public static boolean isFlightIdUnique(String flightId, List<Flight> existingFlights) {
        if (existingFlights == null) return true;
        return existingFlights.stream().noneMatch(f -> f.getId().equals(flightId));
    }

    public static boolean doesPlaneExist(String planeId, List<Plane> existingPlanes) {
        if (planeId == null || existingPlanes == null) return false;
        return existingPlanes.stream().anyMatch(p -> p.getId().equals(planeId));
    }

    public static boolean doesLocationExist(String locationId, List<Location> existingLocations) {
        if (locationId == null || existingLocations == null) return false;
        // Si el ID de ubicación puede ser vacío para escalas opcionales, manejarlo aquí.
        // Por ahora, asume que si se pasa un ID, debe existir.
        if (locationId.trim().isEmpty()) return true; // Considerar si una escala vacía es "existente"
        return existingLocations.stream().anyMatch(loc -> loc.getAirportId().equals(locationId));
    }

    // Método de validación completo para un vuelo (ejemplo conceptual)
    public static boolean isFlightDataValid(String id, String planeId, String departureLocationId,
                                            String arrivalLocationId, String scaleLocationId, /* opcional */
                                            String depYear, String depMonth, String depDay,
                                            int flightHours, int flightMinutes,
                                            int scaleHours, int scaleMinutes, /* solo si scaleLocationId existe */
                                            List<Flight> existingFlights, List<Plane> existingPlanes, List<Location> existingLocations,
                                            String... otherNonEmptyFields) {

        if (!FormatValidationUtils.isValidFlightIdFormat(id)) return false;
        if (!isFlightIdUnique(id, existingFlights)) return false;
        if (!doesPlaneExist(planeId, existingPlanes)) return false;
        if (!doesLocationExist(departureLocationId, existingLocations)) return false;
        if (!doesLocationExist(arrivalLocationId, existingLocations)) return false;
        
        boolean scalePresent = scaleLocationId != null && !scaleLocationId.trim().isEmpty();
        if (scalePresent) {
            if (!doesLocationExist(scaleLocationId, existingLocations)) return false;
        } else {
            // Si no hay escala, la duración de la escala debe ser 0
            if (scaleHours != 0 || scaleMinutes != 0) return false;
        }

        if (!DateUtils.isValidDate(depYear, depMonth, depDay)) return false;
        if (!DateUtils.isTimeGreaterThanZero(flightHours, flightMinutes)) return false;
        // No es necesario validar "otherNonEmptyFields" aquí si no aplica directamente al vuelo en sí,
        // o si los campos obligatorios ya están cubiertos.
        
        return true;
    }
    
    /**
     * Valida si un vuelo existe en la lista de vuelos.
     * @param flightId ID del vuelo a verificar.
     * @param existingFlights Lista de vuelos existentes.
     * @return true si el vuelo existe, false en caso contrario.
     */
    public static boolean doesFlightExist(String flightId, List<Flight> existingFlights) {
        if (flightId == null || existingFlights == null) return false;
        return existingFlights.stream().anyMatch(f -> f.getId().equals(flightId));
    } 
}
