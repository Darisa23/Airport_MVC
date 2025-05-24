/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.services;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.controllers.utils.validators.DateUtils;
import core.controllers.utils.validators.PassengerValidator;
import core.controllers.utils.validators.ValidationUtils;
import core.models.Passenger;
import core.models.storage.StoragePassengers;

/**
 *
 * @author maria
 */


 
public class PassengerService {

    // Ahora, el servicio simplemente crea y almacena, asumiendo que los parámetros ya son válidos y únicos.
    // Recibe los números ya parseados para evitar NumberFormatException aquí.
    public Passenger registerPassenger(long id, String firstName, String lastName, String year, String month, String day,
                                       int countryPhoneCode, long phone, String country) {

        Passenger passenger = new Passenger(
                id, // Recibe el ID ya como long
                firstName,
                lastName,
                DateUtils.buildDate(year, month, day),
                countryPhoneCode, // Recibe el código de país ya como int
                phone, // Recibe el teléfono ya como long
                country
        );

        // La validación de "ya existe" se ha movido al controlador.
        // Si add() falla, aquí asumimos un error interno del almacenamiento, no de validación.
        boolean added = StoragePassengers.getInstance().add(passenger);
        if (!added) {
            // Este caso debería ser extremadamente raro si el controlador hizo bien su trabajo
            // al verificar la existencia antes de llamar a este servicio.
            throw new IllegalStateException("Failed to add passenger to storage despite prior checks by controller.");
        }

        return passenger;
    }

    // getPassenger sigue siendo una operación de recuperación, no de validación de entrada.
    // La responsabilidad de validar si el ID es numérico ANTES de llamar a esto es del controlador.
    public Passenger getPassenger(long id) {
        return StoragePassengers.getInstance().get(id);
    }
}