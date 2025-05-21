/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers.utils;

import java.util.regex.Pattern;

/**
 *
 * @author maria
 */
public class FormatValidationUtils {
       private static final Pattern AIRPORT_ID_PATTERN = Pattern.compile("^[A-Z]{3}$");
    private static final Pattern PLANE_ID_PATTERN = Pattern.compile("^[A-Z]{2}\\d{5}$");
    private static final Pattern FLIGHT_ID_PATTERN = Pattern.compile("^[A-Z]{3}\\d{3}$");

    public static boolean isValidAirportIdFormat(String airportId) {
        if (airportId == null) return false;
        return AIRPORT_ID_PATTERN.matcher(airportId).matches();
    }

    public static boolean isValidPlaneIdFormat(String planeId) {
        if (planeId == null) return false;
        return PLANE_ID_PATTERN.matcher(planeId).matches();
    }

    public static boolean isValidFlightIdFormat(String flightId) {
        if (flightId == null) return false;
        return FLIGHT_ID_PATTERN.matcher(flightId).matches();
    }

    /**
     * Verifica si una cadena que representa un número decimal tiene como máximo un número específico de decimales.
     * @param decimalString La cadena del número decimal.
     * @param maxDecimalPlaces El número máximo de cifras decimales.
     * @return true si el formato es válido, false en caso contrario.
     */
    public static boolean hasMaxDecimalPlaces(String decimalString, int maxDecimalPlaces) {
        if (decimalString == null || decimalString.trim().isEmpty()) {
            return false;
        }
        String trimmed = decimalString.trim();
        int decimalPointIndex = trimmed.indexOf('.');
        if (decimalPointIndex == -1) {
            return true; // No hay decimales, es válido
        }
        return (trimmed.length() - 1 - decimalPointIndex) <= maxDecimalPlaces;
    }
}
