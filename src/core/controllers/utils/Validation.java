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
//*************************************************************
//DEBERÍAMOS PASAR CADA VALIDACIÓN A SU PROBIA CLASE????????????***************************
//***************************************************
public class Validation {
     public static boolean anyEmpty(Object... values) {
        for (Object s : values) {
            if (s == null) return true;
        }
        return false;
    }
     public static boolean isNumericWithDigitRange(String input, int minDigits, int maxDigits) {
    if (input == null || input.trim().isEmpty()) return false;

    String clean = input.trim();

    // Construye la expresión regular dinámicamente: ^\d{min,max}$
    String regex = "^\\d{" + minDigits + "," + maxDigits + "}$";

    return clean.matches(regex);
}
// Valida fechas tipo LocalDate
    public static boolean isValidDate(String input, LocalDate reference) {
        try {
            LocalDate toValidate = LocalDate.parse(input);
            return !toValidate.isBefore(reference);
        } catch (DateTimeParseException | NullPointerException e) {
            return false;
        }
    }

    // Valida fechas tipo LocalDateTime
    
    
}
