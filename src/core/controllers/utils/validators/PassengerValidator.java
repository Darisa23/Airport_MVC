/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers.utils.validators;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.storage.StoragePassengers;

/**
 *
 * @author Alexander Sanguino
 */
public class PassengerValidator implements Validator {

    public static final PassengerValidator INSTANCE = new PassengerValidator();

    private PassengerValidator() {
    }

    @Override
    public Response isValid(String... fields) {
        
        String id = fields[0];
        String year = fields[3];
        String month = fields[4];
        String day = fields[5];
        String countryPhoneCode = fields[6];
        String phone = fields[7];
        
        // 2. ID Validation
        if (!ValidationUtils.isNumericWithDigitRange(id, 1, 15)) {
            return new Response("Invalid passenger ID. It must be numeric, not empty and have at least 15 digits.", Status.BAD_REQUEST);
        }
        
        // 3. CountryPhoneCode Validation
        if (!ValidationUtils.isNumericWithDigitRange(countryPhoneCode, 1, 3)) {
            return new Response("Invalid passenger CountryPhoneCode. It must be numeric, not empty and have at least 3 digits.", Status.BAD_REQUEST);
        }
        // 4. Phone Validation
        if (!ValidationUtils.isNumericWithDigitRange(phone, 1, 11)) {
            return new Response("Invalid passenger Phone. It must be numeric, not empty and have at least 3 digits.", Status.BAD_REQUEST);
        }
        // 5. BirthDate Validation
        if (!DateUtils.isValidDate(year, month, day, true)) {
            return new Response("Invalid BirthDate.", Status.BAD_REQUEST);
        }
        return new Response("Valid passenger",Status.OK);
}
}
