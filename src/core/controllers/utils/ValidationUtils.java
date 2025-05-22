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
 * @author Alexander Sanguino
 */

public class ValidationUtils {
    
 
    public static boolean anyEmpty(String... fields) {
         for (String field : fields) {
            // Un campo está vacío si es null O si después de recortar espacios en blanco, está vacío
            if (field == null || field.trim().isEmpty()) {
                System.out.println("uno vacío");
                return true; // Se encontró al menos un campo vacío
            }
        }
        return false; // Ningún campo estaba vacío
    }

    public static boolean isNumericWithDigitRange(String input, int minDigits, int maxDigits) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }

        String clean = input.trim();

        // Construye la expresión regular dinámicamente: ^\d{min,max}$
        String regex = "^\\d{" + minDigits + "," + maxDigits + "}$";

        return clean.matches(regex);
    }
    public static boolean validNum(double minVal, double maxVal, String val, int cntD) {
    try {
        double numero = Double.parseDouble(val);

        if (minVal > maxVal) return false;
        if (numero < minVal || numero > maxVal) return false;

        // Verificar decimales
        if (val.contains(".")) {
            String[] partes = val.split("\\.");
            if (partes[1].length() > cntD) return false;
        }

        return true;
    } catch (NumberFormatException e) {
        return false;
    }
    }
    public static boolean isValidPositiveLongWithMaxDigits(long number, int maxDigits) {
    if (number < 0) {
        return false;
    }
    return String.valueOf(number).length() <= maxDigits;
}
}
