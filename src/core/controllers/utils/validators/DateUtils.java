/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers.utils.validators;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 *
 * @author maria
 */
public class DateUtils {

    public static boolean isValidDate(String yearStr, String monthStr, String dayStr, boolean before) {
        try {
            int year = Integer.parseInt(yearStr);
            int month = Integer.parseInt(monthStr);
            int day = Integer.parseInt(dayStr);
            
            LocalDate date = LocalDate.of(year, month, day);
            LocalDate today = LocalDate.now();
            LocalDate minDate = today.minusYears(120);
            if (before) {
                return !date.isAfter(today) && !date.isBefore(minDate);
            } else {
                return date.isAfter(today) && date.isBefore(today.plusYears(2));
            }
        } catch (NumberFormatException | DateTimeParseException | NullPointerException e) {
            return false;
        }
    }

    public static boolean isValidDate(String yearStr, String monthStr, String dayStr, String hourStr, String minStr, boolean before) {
        try {
            int year = Integer.parseInt(yearStr);
            int month = Integer.parseInt(monthStr);
            int day = Integer.parseInt(dayStr);
            int hour = Integer.parseInt(hourStr);
            int minutes = Integer.parseInt(minStr);
            
            LocalDateTime date = LocalDateTime.of(year, month, day,hour,minutes);
            LocalDateTime today = LocalDateTime.now();
            LocalDateTime minDate = today.minusYears(120);
            if (before) {
                return !date.isAfter(today) && !date.isBefore(minDate);
            } else {
                return date.isAfter(today) && date.isBefore(today.plusYears(2));
            }
        } catch (NumberFormatException | DateTimeParseException | NullPointerException e) {
            return false; 
        }
    }

    public static LocalDate buildDate(String year, String month, String day) {
        try {
            int yr = Integer.parseInt(year);
            int mnt = Integer.parseInt(month);
            int dy = Integer.parseInt(day);
            
            return LocalDate.of(yr, mnt, dy);
        } catch (Exception e) {
            return null;
        }
    }
    
    public static LocalDateTime buildDate(String year, String month, String day,
            String hour, String minute) {
        try {
            int yr = Integer.parseInt(year);
            int mnt = Integer.parseInt(month);
            int dy = Integer.parseInt(day);
            int hor = Integer.parseInt(hour);
            int min = Integer.parseInt(minute);
            
            return LocalDateTime.of(yr, mnt, dy, hor, min);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isValidDuration( String hoursDurationArrival, String minutesDurationArrival,  String hoursDurationScale, String minutesDurationScale) {
         try {
        int vueloHoras = Integer.parseInt(hoursDurationArrival);
        int vueloMinutos = Integer.parseInt(minutesDurationArrival);
        int escalaHoras = Integer.parseInt(hoursDurationScale);
        int escalaMinutos = Integer.parseInt(minutesDurationScale);

        int duracionVuelo = vueloHoras * 60 + vueloMinutos;
        int duracionEscala = escalaHoras * 60 + escalaMinutos;

        // Si no hay escala (tiempos de escala en 0), validar solo el vuelo
        if (duracionEscala == 0) {
            return duracionVuelo > 0;
        }

        // Si hay escala, el total debe ser mayor a 0
        return (duracionVuelo + duracionEscala) > 0;

    } catch (NumberFormatException e) {
        return false; // Si hay error al convertir a número, no es válido
    }
    }
    

    // Valida una cadena como hora (0-23)
    public static boolean isValidHour(String s) {
        
        if (s == null || s.trim().isEmpty()) {
            return false;
        }
        try {
            int val = Integer.parseInt(s);
            return val > 0 && val <= 23;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Valida una cadena como minuto (0-59)
    public static boolean isValidMinute(String s) {
        if (s == null || s.trim().isEmpty()) {
            return false;
        }
        try {
            int val = Integer.parseInt(s);
            return val > 0 && val <= 59;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    
}
