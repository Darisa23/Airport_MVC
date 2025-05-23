/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers.utils.validators;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.storage.StoragePlanes;

/**
 *
 * @author maria
 */
public class PlaneValidator implements Validator {

    public static final PlaneValidator INSTANCE = new PlaneValidator();

    private PlaneValidator() {
    }

    @Override
    public Response isValid(String... fields) {    
        String id = fields[0];
        String brand = fields[1];
        String model = fields[2];
        String maxCapacity = fields[3];
        String airline = fields[4];
    
        
          if (ValidationUtils.anyEmpty(id, brand,model,maxCapacity, airline)) {
                return new Response("Fields cannot be empty", Status.BAD_REQUEST);
            }
            //2.  ID Validation
            if (!ValidationUtils.validId(id, 2, 5)) {
                return new Response("Invalid plane ID format. Must match XXYYYYY (e.g., AB12345)", Status.BAD_REQUEST);
            }  
            if (StoragePlanes.getInstance().get(id) != null) {
                return new Response("There is already an airplane with that ID", Status.BAD_REQUEST);
            }
            int mxCap = Integer.parseInt(maxCapacity);
            if (mxCap<= 0) {
                return new Response("Max capacity must be greater than 0", Status.BAD_REQUEST);
            }

     return new Response("Valid plane",Status.OK);
}}
