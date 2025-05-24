/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.controllers.utils.validators.PlaneValidator;
import core.controllers.utils.validators.ValidationUtils;
import core.models.Plane;
import core.models.storage.StoragePlanes;
import core.services.PlaneService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author maria
 */
public class PlaneController {
    
   private final PlaneService planeService;

    public PlaneController() {
        this.planeService = new PlaneService();
    }


    public Response createPlane(String id, String brand, String model, String maxCapacity, String airline) {
        try {
            // 1. Validaciones de presencia (campos vacíos)
            if (ValidationUtils.anyEmpty(id, brand, model, maxCapacity, airline)) {
                return new Response("Fields cannot be empty", Status.BAD_REQUEST);
            }

            // 2. Validaciones de formato y otras reglas específicas (con PlaneValidator)
            Response validationResponse = PlaneValidator.INSTANCE.isValid(id, brand, model, maxCapacity, airline);
            if (validationResponse.getStatus() != Status.OK) {
                return validationResponse;
            }

            // 3. Parseo de String a Integer (maxCapacity).
            int parsedMaxCapacity;
            try {
                parsedMaxCapacity = Integer.parseInt(maxCapacity);
            } catch (NumberFormatException e) {
                return new Response("Invalid max capacity format. Capacity must be a number.", Status.BAD_REQUEST);
            }

            // 4. Validación de negocio: Verificar si ya existe el avión (usando el servicio para consultar).
            if (planeService.getPlane(id) != null) { // <-- Esta línea es la que verifica.
    return new Response("There is already a plane with that ID.", Status.BAD_REQUEST);
}

            // 5. Si todas las validaciones pasaron, llamar al servicio.
            // Le pasamos los valores ya parseados.
            Plane newPlane = planeService.registerPlane(id, brand, model, parsedMaxCapacity, airline);

            return new Response("Plane created successfully", Status.CREATED, newPlane);

        } catch (IllegalStateException e) {
            return new Response("Internal server error during plane creation: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new Response("An unexpected error occurred: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response getPlane(String id) {
        try {
            Plane plane = StoragePlanes.getInstance().get(id);
            if (plane == null) {
                return new Response("Plane not found", Status.NOT_FOUND);
            }
            return new Response("Plane retrieved successfully", Status.OK, plane);

        } catch (Exception e) {
            return new Response("Error retrieving plane: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }
    //obtener todos los id de aviones:
    public Response getAllPlaneIds() {
    ArrayList<Plane> planes = StoragePlanes.getInstance().getAll();
    List<String> ids = planes.stream()
                                 .map(p -> String.valueOf(p.getId()))
                                 .toList(); 
    return new Response("Plane IDs retrieved", Status.OK, ids);
}
    public Response getAllPlanes() {
        try {
            ArrayList<Plane> planes = StoragePlanes.getInstance().getAll();
            Collections.sort(planes, Comparator.comparing(Plane::getId));
            return new Response("Planes retrieved successfully", Status.OK, planes);

        } catch (Exception e) {
            return new Response("Error retrieving planes list: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }
    public static Response addPlane(Plane plane) {
        try {
            boolean added = StoragePlanes.getInstance().add(plane);
            if (!added) {
                return new Response("This plane already exists", Status.BAD_REQUEST);
            }
            return new Response("Plane added successfully", Status.OK);

        } catch (Exception e) {
            return new Response("Error retrieving plane: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    public static Response updatePlane(String id, String newBrand, String newModel, String newAirline) {
        try {
           
            Response planeRes = getPlane(id);
            if(planeRes.getStatus()== Status.NOT_FOUND){
                return planeRes;
            }
            Plane plane = (Plane) planeRes.getObject();
            Response Invalid = PlaneValidator.INSTANCE.isValid(id,newBrand,newModel,newAirline);
            if(Invalid.getStatus() != Status.OK){
            return Invalid;}
                
            plane.setBrand(newBrand);
            plane.setModel(newModel);         
            plane.setAirline(newAirline);

            return new Response("Plane updated successfully", Status.OK);

        } catch (Exception e) {
            return new Response("Error updating plane: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response deletePlane(String id) {
        try {
            Plane plane = StoragePlanes.getInstance().get(id);

            if (plane == null) {
                return new Response("Plane not found", Status.NOT_FOUND);
            }

            //StoragePlanes.getInstance().delete();
            return new Response("Plane deleted successfully", Status.OK);

        } catch (Exception e) {
            return new Response("Error deleting plane: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }
    
}
