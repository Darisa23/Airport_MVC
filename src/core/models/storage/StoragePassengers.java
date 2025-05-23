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

public boolean delete(Passenger passenger) {
    if (passenger == null) {
        return false;
    }
    // Corrected line: p.getId() (primitive long) will be autoboxed to Long
    // to call the equals() method on passenger.getId() (which is also likely a Long or autoboxed)
    return passengers.removeIf(p -> p.getId() == passenger.getId() || Long.valueOf(p.getId()).equals(passenger.getId()));
}
      @Override
public boolean update(Passenger updatedPassenger) {
    if (updatedPassenger == null) {
        return false;
    }

    for (int i = 0; i < passengers.size(); i++) {
        // Corrected line: Explicitly box the primitive long to a Long object to use .equals()
        // Or, for direct value comparison, use == if both are primitive long
        if (Long.valueOf(passengers.get(i).getId()).equals(updatedPassenger.getId())) {
            passengers.set(i, updatedPassenger); // Reemplaza la instancia antigua con la nueva
            return true;
        }
    }
    return false; // El pasajero a actualizar no fue encontrado
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
