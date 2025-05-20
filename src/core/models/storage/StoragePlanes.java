/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

import core.models.Plane;
import java.util.ArrayList;

/**
 *
 * @author Alexander Sanguino
 */
public class StoragePlanes implements Storage<Plane,String> {

    private static StoragePlanes instance;
    private final ArrayList<Plane> airplanes;

    private StoragePlanes() {
        this.airplanes = new ArrayList<>();
    }

    public static StoragePlanes getInstance() {
        if (instance == null) {
            instance = new StoragePlanes();
        }
        return instance;
    }

    @Override
    public boolean add(Plane plane) {
         Plane pl = this.get(plane.getId()); //revisamos si ya estaba:
         if(pl != null){
                return false;
            }
         //Lo agregamos si no está
        airplanes.add(plane);
        return true;
    }

    @Override
    public boolean delete() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean update() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Plane get(String id) { 
        for (Plane pl : airplanes){
            if(pl.getId().equals(id)){
                return pl;
            }
        }
        return null;}

    @Override
    public ArrayList<Plane> getAll() {
        return new ArrayList<>(airplanes);
    }

}
