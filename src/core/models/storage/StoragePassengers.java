/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

import core.models.Passenger;
import java.util.ArrayList;

/**
 *
 * @author Alexander Sanguino
 */
public class StoragePassengers implements Storage<Passenger,Long> {

     private static StoragePassengers instance;
    private ArrayList<Passenger> passengers;

    private StoragePassengers() {
        this.passengers = new ArrayList<>();
    }

    public static StoragePassengers getInstance() {
        if (instance == null) {
            instance = new StoragePassengers();
        }
        return instance;
    }

    @Override
    public boolean add(Passenger passenger) {
         Passenger ps=this.get(passenger.getId()); //revisamos si ya estaba:
         if(ps != null){
                return false;
            }
         //Lo agregamos si no está
        this.passengers.add(passenger);
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
    public Passenger get(Long id) { 
        for (Passenger ps : passengers){
            if(ps.getId()==id){
                return ps;
            }
        }
        return null;}

    @Override
    public ArrayList<Passenger> getAll() {
        return new ArrayList<>(passengers);
    }

    
}
