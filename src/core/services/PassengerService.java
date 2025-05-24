/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.services;

import static core.controllers.PassengerController.addPassenger;
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
    
    public Response registerPassenger(String id, String firstName, String lastName, String year, String month, String day,
                                      String countryPhoneCode, String phone, String country) {

        try {
            // 1. Validación de campos vacíos
            if (ValidationUtils.anyEmpty(id, firstName, lastName, year, month, day, countryPhoneCode, phone, country)) {
                return new Response("Fields cannot be empty", Status.BAD_REQUEST);
            }

            // 2. Verificar si ya existe el pasajero
            if (StoragePassengers.getInstance().get(Long.valueOf(id)) != null) {
                return new Response("There is already a passenger with that ID.", Status.BAD_REQUEST);
            }

            // 3. Validación del validador
            Response invalid = PassengerValidator.INSTANCE.isValid(id, firstName, lastName, year, month, day, countryPhoneCode, phone, country);
            if (invalid.getStatus() != Status.OK) {
                return invalid;
            }

            // 4. Crear el objeto Passenger
            Passenger passenger = new Passenger(
                    Long.parseLong(id),
                    firstName,
                    lastName,
                    DateUtils.buildDate(year, month, day),
                    Integer.parseInt(countryPhoneCode),
                    Long.parseLong(phone),
                    country
            );

            // 5. añadir
            addPassenger(passenger);

            return new Response("Passenger created successfully", Status.CREATED, passenger);

        } catch (Exception e) {
            return new Response("Error Registering passenger: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }
}
