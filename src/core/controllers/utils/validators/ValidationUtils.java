/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers.utils.validators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        return false; 
    }

    public static boolean isNumericWithDigitRange(String input, int minDigits, int maxDigits) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }

        String clean = input.trim();

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
    
    public static List<String> sortList(List<String> list, int letterCount, int numberCount) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>(list != null ? list : Collections.emptyList());
        }

        // Validar que el formato sea consistente
        int expectedLength = letterCount + numberCount;

        return list.stream()
            .filter(id -> id != null && id.length() == expectedLength)
            .sorted((id1, id2) -> {
                // Extraer parte alfabética
                String letterPart1 = id1.substring(0, letterCount);
                String letterPart2 = id2.substring(0, letterCount);

                // Comparar primero por parte alfabética
                int letterComparison = letterPart1.compareTo(letterPart2);
                if (letterComparison != 0) {
                    return letterComparison;
                }

                // Si las partes alfabéticas son iguales, comparar por parte numérica
                try {
                    int num1 = Integer.parseInt(id1.substring(letterCount));
                    int num2 = Integer.parseInt(id2.substring(letterCount));
                    return Integer.compare(num1, num2);
                } catch (NumberFormatException e) {
                    // Si no se puede parsear como número, ordenar alfabéticamente
                    return id1.substring(letterCount).compareTo(id2.substring(letterCount));
                }
            })
            .collect(Collectors.toList());
    }
}
