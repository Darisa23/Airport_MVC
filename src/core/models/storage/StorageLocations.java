/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

import core.models.Location;
import java.util.ArrayList;

/**
 *
 * @author Alexander Sanguino
 */
public class StorageLocations implements Storage<Location,String> {

    
    private static StorageLocations instance;
    private final ArrayList<Location> airports;

    private StorageLocations() {
        this.airports = new ArrayList<>();
    }

    public static StorageLocations getInstance() {
        if (instance == null) {
            instance = new StorageLocations();
        }
        return instance;
    }

    @Override
    public boolean add(Location location) {
         Location loc = this.get(location.getAirportId()); //revisamos si ya estaba:
         if(loc != null){
                return false;
            }
         //Lo agregamos si no está
        this.airports.add(location);
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
    public Location get(String id) { 
        for (Location loc : airports){
            if(loc.getAirportId().equals(id)){
                return loc;
            }
        }
        return null;}
//ESTO ACÁ DEBE DEVOLVER UNA COPIAAAAAAAAAA*****************************
    @Override
    public ArrayList<Location> getAll() {
        return new ArrayList<>(airports);
    }
}
