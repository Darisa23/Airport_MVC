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

   
}
