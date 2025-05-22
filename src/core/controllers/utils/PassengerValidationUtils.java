/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers.utils;

import core.models.Passenger;
import java.util.List;

/**
 *
 * @author maria
 */
class PassengerValidationUtils {

    public static boolean isPassengerIdUnique(long passengerId, List<Passenger> existingPassengers) {
        if (existingPassengers == null) return true; // O lanzar excepción si la lista no debería ser null
        return existingPassengers.stream().noneMatch(p -> p.getId() == passengerId);
    }

    public static boolean isValidPassengerId(long passengerId) {
        return ValidationUtils.isValidPositiveLongWithMaxDigits(passengerId, 15);
    }

    public static boolean isValidPhoneCode(int phoneCode) {
        // Reutilizando la lógica general, asumiendo que phoneCode es int
        // Si phoneCode es String en la entrada, usar isNumericWithDigitRange
        if (phoneCode < 0) return false;
        return String.valueOf(phoneCode).length() <= 3;
    }
    
    public static boolean isValidPhoneNumber(long phoneNumber) {
         return ValidationUtils.isValidPositiveLongWithMaxDigits(phoneNumber, 11);
    }

    // Método de validación completo para un pasajero (ejemplo conceptual)
    public static boolean isPassengerDataValid(long id, String birthYear, String birthMonth, String birthDay,
                                               int phoneCode, long phoneNumber, List<Passenger> existingPassengers,
                                               String... otherNonEmptyFields) {
        if (!isValidPassengerId(id)) return false;
        if (!isPassengerIdUnique(id, existingPassengers)) return false;
        if (!DateUtils.isValidDate(birthYear, birthMonth, birthDay)) return false;
        if (!isValidPhoneCode(phoneCode)) return false;
        if (!isValidPhoneNumber(phoneNumber)) return false;
        if (GeneralValidationUtils.areAnyFieldsEmpty(otherNonEmptyFields)) return false;
        return true;
    }
}