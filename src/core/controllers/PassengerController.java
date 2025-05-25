/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;
import core.controllers.utils.validators.DateUtils;
import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.controllers.utils.validators.PassengerValidator;
import core.controllers.utils.validators.ValidationUtils;
import core.models.Flight;
import core.models.Observers.Observer;
import core.services.PassengerService;

/**
 *
 * @author maria
 */
public class PassengerController {

    private final PassengerService passengerService;

    public PassengerController() {
        this.passengerService = new PassengerService();
    }

    public Response registerPassenger(String id, String firstName, String lastName, String year, String month, String day,
            String countryPhoneCode, String phone, String country) {
        try {
            // 1. Validaciones de presencia (campos vacíos)
            if (ValidationUtils.anyEmpty(id, firstName, lastName, year, month, day, countryPhoneCode, phone, country)) {
                return new Response("Fields cannot be empty", Status.BAD_REQUEST);
            }

            // 2. Validaciones de formato y otras reglas específicas (con PassengerValidator)
            Response validationResponse = PassengerValidator.INSTANCE.isValid(id, firstName, lastName, year, month, day, countryPhoneCode, phone, country);
            if (validationResponse.getStatus() != Status.OK) {
                return validationResponse;
            }

            // 3. Parseo de Strings a tipos numéricos (ID, teléfono, código de país).
 
            long parsedId;
            int parsedCountryPhoneCode;
            long parsedPhone;
            try {
                parsedId = Long.parseLong(id);
                parsedCountryPhoneCode = Integer.parseInt(countryPhoneCode);
                parsedPhone = Long.parseLong(phone);
            } catch (NumberFormatException e) {
                return new Response("Invalid number format for ID, phone, or country code.", Status.BAD_REQUEST);
            }

            if (passengerService.getPassenger(parsedId)!=null) {
                return new Response("There is already a passenger with that ID.", Status.BAD_REQUEST);
            }

            // 5. Si todas las validaciones pasaron, llamar al servicio.
            // Le pasamos los valores ya parseados.
            passengerService.registerPassenger(
                    parsedId, firstName, lastName, DateUtils.buildDate(year, month, day), parsedCountryPhoneCode, parsedPhone, country);


            return new Response("Passenger created successfully", Status.CREATED);

        } catch (IllegalArgumentException e) {
            return new Response("Invalid data provided: " + e.getMessage(), Status.BAD_REQUEST);
        } catch (IllegalStateException e) {
            return new Response("Internal server error during passenger registration: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new Response("An unexpected error occurred: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

//obtener todos los id de pasajeros:

    public Response getAllPassengerIds() {
    return new Response("Passenger IDs retrieved", Status.OK, passengerService.allPassengers());
    }

    public Response updatePassenger(String id, String newFirstName, String newLastName,
            String newYear, String newMonth, String newDay, String newCountryCode, String newPhone, String newCountry) {

        try {
            if (id.isEmpty()) {
                return new Response("Debe seleccionar un Id de usuario para actualizar su información", Status.NO_CONTENT);
            }
            if (passengerService.getPassenger(Long.parseLong(id))==null) {
                return new Response("No existe pasajero con el Id seleccionado",Status.NOT_FOUND);
            }
            if (ValidationUtils.anyEmpty(id, newFirstName,newLastName,newYear, newMonth, newDay, newCountryCode, newPhone, newCountry)) {
            return new Response("Fields cannot be empty", Status.BAD_REQUEST);
        }
            if (ValidationUtils.anyEmpty(id, newFirstName, newLastName, newYear, newMonth, newDay, newCountryCode, newPhone, newCountry)) {
                return new Response("Fields cannot be empty", Status.BAD_REQUEST);
            }
            Response Invalid = PassengerValidator.INSTANCE.isValid(id, newFirstName, newLastName, newYear, newMonth,
                    newDay, newCountryCode, newPhone, newCountry);
            if (Invalid.getStatus() != Status.OK) {
                return Invalid;
            }
            passengerService.update(Long.parseLong(id),
                    newFirstName,
                    newLastName,
                    DateUtils.buildDate(newYear, newMonth, newDay),
                    Integer.parseInt(newCountryCode),
                    Long.parseLong(newPhone),
                    newCountry);
            return new Response("Passenger updated successfully", Status.OK);
        } catch (Exception e) {
            return new Response("Error updating passenger: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    public Response addToFlight(String passengerId, Flight flight) {
        try {
            if (passengerService.getPassenger(Long.parseLong(passengerId))==null) {
                return new Response("No existe pasajero con el Id seleccionado",Status.NOT_FOUND);
            }
            boolean added = passengerService.addAflight(Long.parseLong(passengerId),flight);
            if (!added) {
                return new Response("Passenger is already on this flight", Status.BAD_REQUEST);
            }
            return new Response("Flight added to passenger successfully", Status.OK,passengerService.getPassenger(Long.parseLong(passengerId)));
        } catch (Exception e) {
            return new Response("Error adding flight to passenger: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    public void registerObserver(Observer observer) {
        passengerService.addObserver(observer);
    }

    public Response getPassengerData(String id) {
        if (passengerService.getPassenger(Long.parseLong(id))==null) {
            return new Response("Passenger not found", Status.NOT_FOUND);
        }
        return new Response("Data ready", Status.OK, passengerService.specificData(Long.parseLong(id)));
}
    
    public Response getFlightRowsOfPassenger(String id) {
     if (passengerService.getPassenger(Long.parseLong(id))==null) {
            return new Response("Passenger not found", Status.NOT_FOUND);
        }
    return new Response("Flights refreshed", Status.OK, passengerService.flightsOf(Long.parseLong(id)));
}
    
public Response getPassengers() {
    return new Response("Passengers refreshed", Status.OK, passengerService.completeInfo());
}
}
