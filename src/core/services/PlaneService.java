/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.services;

import core.controllers.utils.validators.ValidationUtils;
import core.models.Observers.Observer;
import core.models.Passenger;
import core.models.Plane;
import core.models.storage.StoragePassengers;
import core.models.storage.StoragePlanes;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author maria
 */
public class PlaneService {

    public Plane registerPlane(String id, String brand, String model, int maxCapacity, String airline) {
        Plane plane = new Plane(id, brand, model, maxCapacity, airline);

        boolean added = StoragePlanes.getInstance().add(plane);
        if (!added) {
            throw new IllegalStateException("Failed to add plane to storage. Controller should have checked for existence.");
        }
        return plane;
    }

    public Plane getPlane(String id) {
  
        return StoragePlanes.getInstance().get(id);
    }

    public List<String> allPlanes() {

        List<String> ids = StoragePlanes.getInstance()
                .getAll().stream()
                .map(Plane::getId)
                .toList();

        // Para formato "AB12345" (2 letras, 5 números)
        return ValidationUtils.sortList(ids, 2, 5);
    }

    public void update(String id, String newBrand, String newModel,int newMaxCapacity, String newAirline) {
       Plane updatedPlane = registerPlane(id,newBrand,newModel,newMaxCapacity,newAirline);
           StoragePlanes.getInstance().update(updatedPlane);
    }
    public ArrayList<Object[]> completeInfo(){
          ArrayList<Object[]> rows = new ArrayList<>();

        for (Plane p : StoragePlanes.getInstance().getAll()) {
            Object[] row = new Object[]{
                p.getId(),
                p.getBrand(),
                p.getModel(),
                p.getMaxCapacity(),
                p.getAirline(),
                p.getNumFlights()
            };
            rows.add(row);
        }
        return rows;
    }
        public void addObserver(Observer observer){
        StoragePlanes.getInstance().addObserver(observer);
    }
       

}
