/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.controllers.utils.ValidationUtils;
import core.models.Plane;
import core.models.storage.StoragePlanes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author maria
 */
public class PlaneController {
    //********************************************************************
    // PASAR ESTO A VALIDATION
    //LO HACES GENÉRICO PARA REUTILIZARLO EN LOS VUELOS
    //*******************************************************************
    private static boolean isValidPlaneId(String id) {
        return id != null && id.matches("^[A-Z]{2}\\d{5}$");
    }
    
    public Response createPlane(String id, String brand, String model, String maxCapacity, String airline) {
        try {
            // Validaciones
            //1. No empty fields
            if (ValidationUtils.anyEmpty(id, brand,model,maxCapacity, airline)) {
                return new Response("Fields cannot be empty", Status.BAD_REQUEST);
            }
            //2.  ID Validation
            if (!isValidPlaneId(id)) {
                return new Response("Invalid plane ID format. Must match XXYYYYY (e.g., AB12345)", Status.BAD_REQUEST);
            }  
            if (StoragePlanes.getInstance().get(id) != null) {
                return new Response("There is already an airplane with that ID", Status.BAD_REQUEST);
            }
            int mxCap = Integer.parseInt(maxCapacity);
            if (mxCap<= 0) {
                return new Response("Max capacity must be greater than 0", Status.BAD_REQUEST);
            }

            Plane plane = new Plane(id, brand, model, mxCap, airline);
            StoragePlanes.getInstance().add(plane);

            return new Response("Plane created successfully", Status.CREATED);

        } catch (Exception e) {
            return new Response("Error creating plane: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }
//LOS DEJAMOS Y LOS USAMOS O LOS QUITAMOS???*******************************************************************
    //CREO QUE HAY QUE DEJARLOS, ENTONCES CAMBIA DONDE LLAMO ARRIBA DIRECTAMENTE AL STORAGE, LO MISMO CON LOS OTROS
    //*************************************************************************************
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

    public static Response getAllPlanes() {
        try {
            ArrayList<Plane> planes = StoragePlanes.getInstance().getAll();
            Collections.sort(planes, Comparator.comparing(Plane::getId));
            return new Response("Planes retrieved successfully", Status.OK, planes);

        } catch (Exception e) {
            return new Response("Error retrieving planes list: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }
//REVISAR Y ARREGLAR LUEGO:
    public static Response updatePlane(String id, String newBrand, String newModel, String newAirline) {
        try {
            Plane plane = StoragePlanes.getInstance().get(id);

            if (plane == null) {
                return new Response("Plane not found", Status.NOT_FOUND);
            }

            /*if (!isNonEmpty(newBrand) || !isNonEmpty(newModel) || !isNonEmpty(newAirline)) {
                return new Response("Fields must not be empty", Status.BAD_REQUEST);
            }*/

            // Actualizar propiedades
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

            StoragePlanes.getInstance().delete();
            return new Response("Plane deleted successfully", Status.OK);

        } catch (Exception e) {
            return new Response("Error deleting plane: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }
}
