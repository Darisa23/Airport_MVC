/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

import core.models.Observers.Observable;
import core.models.Observers.Observer;
import core.models.Passenger;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alexander Sanguino
 */
public class StoragePassengers implements Storage<Passenger,Long>, Observable {

     private static StoragePassengers instance;
    private ArrayList<Passenger> passengers;

    private final List<Observer> observers = new ArrayList<>();
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
        notifyObservers();
        return true;
    }

    @Override

public boolean delete(Passenger passenger) {
    if (passenger == null) {
        return false;
    }
    return passengers.removeIf(p -> p.getId() == passenger.getId() || Long.valueOf(p.getId()).equals(passenger.getId()));
}
      @Override
public boolean update(Passenger updatedPassenger) {
    if (updatedPassenger == null) {
        return false;
    }

    for (int i = 0; i < passengers.size(); i++) {
        if (Long.valueOf(passengers.get(i).getId()).equals(updatedPassenger.getId())) {
            passengers.set(i, updatedPassenger);          
            notifyObservers(); 
            System.out.println("se notificó en update");
            return true;
        }
    }
    return false; 
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
     @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update();
        }
    }

     @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }
    
}
