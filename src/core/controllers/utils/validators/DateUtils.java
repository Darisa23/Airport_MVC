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
            return false; // One of the values is not numeric or the date is invalid (like 31/02)
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
            return false; // One of the values is not numeric or the date is invalid (like 31/02)
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

    public static boolean isTimeGreaterThanZero(int hours, int minutes) {
        return hours > 0 || minutes > 0;
        
    }
    
    // Adds a specified number of hours to a LocalDateTime
    public static LocalDateTime addHours(LocalDateTime dateTime, int hoursToAdd) {
        if (dateTime == null) {
            throw new IllegalArgumentException("Original LocalDateTime cannot be null.");
        }
        return dateTime.plusHours(hoursToAdd);
    }

    // Adds a specified number of minutes to a LocalDateTime
    public static LocalDateTime addMinutes(LocalDateTime dateTime, int minutesToAdd) {
        if (dateTime == null) {
            throw new IllegalArgumentException("Original LocalDateTime cannot be null.");
        }
        return dateTime.plusMinutes(minutesToAdd);
    }
    
    // Valida una cadena como hora (0-23)
    public static boolean isValidHour(String s) {
        if (s == null || s.trim().isEmpty()) {
            return false;
        }
        try {
            int val = Integer.parseInt(s);
            return val >= 0 && val <= 23;
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
            return val >= 0 && val <= 59;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    
}
