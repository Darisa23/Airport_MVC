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
 * @author Alexander Sanguino
 */

public class ValidationUtils {
    
 
    public static boolean anyEmpty(String... fields) {
         for (String field : fields) {
            // A field is empty if it is null OR if, after trimming whitespace, it is empty
            if (field == null || field.trim().isEmpty()) {
                System.out.println("uno vacío");
                return true; // At least one empty field was found
            }
        }
        return false; // No fields were empty
    }

    public static boolean isNumericWithDigitRange(String input, int minDigits, int maxDigits) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }

        String clean = input.trim();

        // builds the regular expression dynamicaally: ^\d{min,max}$
        String regex = "^\\d{" + minDigits + "," + maxDigits + "}$";

        return clean.matches(regex);
    }
    public static boolean validNum(double minVal, double maxVal, String val, int cntD) {
    try {
        double numero = Double.parseDouble(val);

        if (minVal > maxVal) return false;
        if (numero < minVal || numero > maxVal) return false;

        // verify decimals
        if (val.contains(".")) {
            String[] partes = val.split("\\.");
            if (partes[1].length() > cntD) return false;
        }

        return true;
    } catch (NumberFormatException e) {
        return false;
    }
    }
    public static boolean validId(String input, int letras, int digitos) {
    if (input == null) return false;
    String regex = "^" + "[A-Z]{" + letras + "}" + "\\d{" + digitos + "}$";
    return input.matches(regex);
    }
    public static boolean isValidPositiveLongWithMaxDigits(long number, int maxDigits) {
    if (number < 0) {
        return false;
    }
    return String.valueOf(number).length() <= maxDigits;
}
}
