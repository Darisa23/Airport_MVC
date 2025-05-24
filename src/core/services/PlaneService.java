/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.services;

import core.models.Plane;
import core.models.storage.StoragePlanes;

/**
 *
 * @author maria
 */


  

public class PlaneService {

    // registerPlane ahora recibe los tipos de datos ya parseados y validados por el controlador.
    public Plane registerPlane(String id, String brand, String model, int maxCapacity, String airline) {
        Plane plane = new Plane(id, brand, model, maxCapacity, airline);

        boolean added = StoragePlanes.getInstance().add(plane);
        if (!added) {
            throw new IllegalStateException("Failed to add plane to storage. Controller should have checked for existence.");
        }
        return plane;
    }

    // getPlane simplemente recupera, sin validaciones de entrada.
    public Plane getPlane(String id) {
        return StoragePlanes.getInstance().get(id);
    }
}
