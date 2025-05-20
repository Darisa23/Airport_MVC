/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;
import core.models.Flight;
import java.util.ArrayList;

/**
 *
 * @author Alexander Sanguino
 */
public class StorageFlights implements Storage<Flight,String>{
    
    private static StorageFlights instance;
    private ArrayList<Flight> flights;

    private StorageFlights() {
        this.flights = new ArrayList<>();
    }

    public static StorageFlights getInstance() {
        if (instance == null) {
            instance = new StorageFlights();
        }
        return instance;
    }

    @Override
    public boolean add(Flight flight) {
         Flight fl = this.get(flight.getId()); //revisamos si ya estaba:
         if(fl != null){
                return false;
            }
         //Lo agregamos si no está
        flights.add(flight);
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
    public Flight get(String id) { 
        for (Flight fl : flights){
            if(fl.getId().equals(id)){
                return fl;
            }
        }
        return null;}

    @Override
    public ArrayList<Flight> getAll() {
        return new ArrayList<>(flights);
    }

}
