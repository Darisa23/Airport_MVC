/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 *
 * @author maria
 */

public class DateUtils {
  public static boolean isValidDate(String yearStr, String monthStr, String dayStr) {
        try {
            int year = Integer.parseInt(yearStr);
            int month = Integer.parseInt(monthStr);
            int day = Integer.parseInt(dayStr);

            LocalDate date = LocalDate.of(year, month, day);
            LocalDate today = LocalDate.now();
            LocalDate minDate = today.minusYears(120);

            return !date.isAfter(today) && !date.isBefore(minDate);
        } catch (NumberFormatException | DateTimeParseException | NullPointerException e) {
            return false; // Alguno de los valores no es numérico o la fecha es inválida (como 31/02)
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

}
