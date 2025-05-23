/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.controllers.utils.validators.PlaneValidator;
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
    //creating plane
    public Response createPlane(String id, String brand, String model, String maxCapacity, String airline) {
        try {
            Response Invalid = PlaneValidator.INSTANCE.isValid(id,brand,model,maxCapacity,airline);
            if (Invalid.getStatus()!=Status.OK){
                return Invalid;
            }

            Plane plane = new Plane(id, brand, model,  Integer.parseInt(maxCapacity), airline);
            addPlane(plane);

            return new Response("Plane created successfully", Status.CREATED);

        } catch (Exception e) {
            return new Response("Error creating plane: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
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

            StoragePlanes.getInstance().delete();
            return new Response("Plane deleted successfully", Status.OK);

        } catch (Exception e) {
            return new Response("Error deleting plane: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }
    
}
