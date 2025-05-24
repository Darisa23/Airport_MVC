/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.services;

import core.controllers.PlaneController;
import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.controllers.utils.validators.PlaneValidator;
import core.models.Plane;

/**
 *
 * @author maria
 */
public class PlaneService {

    public Response createPlane(String id, String brand, String model, String maxCapacity, String airline) {
        try {
            Response invalid = PlaneValidator.INSTANCE.isValid(id, brand, model, maxCapacity, airline);
            if (invalid.getStatus() != Status.OK) {
                return invalid;
            }

            Plane plane = new Plane(id, brand, model, Integer.parseInt(maxCapacity), airline);
            return PlaneController.addPlane(plane);

        } catch (Exception e) {
            return new Response("Error creating plane: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }
}
