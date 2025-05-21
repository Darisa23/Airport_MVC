/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers.utils;
    import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 * @author maria
 */
public class GeneralValidationUtils {

 /**
 * Checks if any of the provided text fields are null or empty after trimming spaces.
 * @param fields Text fields to check.
 * @return true if at least one field is empty, false otherwise.
 */
public static boolean areAnyFieldsEmpty(String... fields) {
    for (String field : fields) {
        if (field == null || field.trim().isEmpty()) {
            return true;
        }
    }
    return false;
}

/**
 * Checks if a string is numeric and meets a specific digit range.
 * @param input The string to check.
 * @param minDigits Minimum number of digits.
 * @param maxDigits Maximum number of digits.
 * @return true if the string is numeric and within the digit range, false otherwise.
 */
public static boolean isNumericWithDigitRange(String input, int minDigits, int maxDigits) {
    if (input == null || input.trim().isEmpty()) {
        return false;
    }
    String cleanInput = input.trim();
    if (!cleanInput.matches("\\d+")) { // Checks if all characters are digits
        return false;
    }
    return cleanInput.length() >= minDigits && cleanInput.length() <= maxDigits;
}

/**
 * Checks if a long number is greater than or equal to 0 and has a maximum number of digits.
 * @param number The number to check.
 * @param maxDigits Maximum allowed number of digits.
 * @return true if the number meets the conditions, false otherwise.
 */
public static boolean isValidPositiveLongWithMaxDigits(long number, int maxDigits) {
    if (number < 0) {
        return false;
    }
    return String.valueOf(number).length() <= maxDigits;
}
}