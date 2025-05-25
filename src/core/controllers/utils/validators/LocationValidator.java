/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers.utils.validators;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.storage.StorageLocations;

/**
 *
 * @author Alexander Sanguino
 */
public class LocationValidator implements Validator {
    public static final LocationValidator INSTANCE = new LocationValidator();

    private LocationValidator() {
    }

    @Override
    public Response isValid(String... fields) {
        
        String id = fields[0];
        String name = fields[1];
        String city = fields[2];
        String country = fields[3];
        String latitude = fields[4];
        String longitude = fields[5];
        if (ValidationUtils.anyEmpty(id, name, city, country,latitude,longitude)) {
                return new Response("Fields cannot be empty", Status.BAD_REQUEST);
            }
            //2. ID Validation
            if (!id.matches("[A-Z]{3}")) {
                return new Response("Airport Id must be 3 Uppercase letters", Status.BAD_REQUEST);
            }
            if (StorageLocations.getInstance().get(id) != null) {
                return new Response("There is already an airport with that ID.", Status.BAD_REQUEST);
            }
            //3. Check latitude and longitude
            if (!ValidationUtils.validNum(-90, 90, latitude, 4)){
                return new Response("Airport latitude must be a number between -90 and 90, with up to 4 decimals separated by a point or comma (optional)", Status.BAD_REQUEST);
            }
            if (!ValidationUtils.validNum(-180, 180, longitude, 4)) {
                return new Response("Airport longitude must be a number between -180 and 180, with up to 4 decimals separated by a point or comma (optional)", Status.BAD_REQUEST);
            }
            double[] location = new double[]{ValidationUtils.buildNum(latitude),ValidationUtils.buildNum(longitude)};
        return new Response("Valid location",Status.OK,location);
}
}
