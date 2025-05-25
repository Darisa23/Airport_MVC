/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.services;

import core.controllers.utils.validators.DateUtils;
import core.controllers.utils.validators.ValidationUtils;
import core.models.Flight;
import core.models.Location;
import core.models.Observers.Observer;
import core.models.Passenger;
import core.models.Plane;
import core.models.storage.StorageFlights;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author maria
 */
// FlightService.java
public class FlightService {
    public Flight registerFlight(String id, Plane plane, Location departureLocation, Location arrivalLocation, Location scaleLocation,
                                  String year, String month, String day, String hour, String minutes,
                                  int hoursDurationArrival, int minutesDurationArrival,
                                  int hoursDurationScale, int minutesDurationScale) {
        Flight flight;

  
        if (scaleLocation == null) {
           
            flight = new Flight(id,
                    plane,
                    departureLocation,
                    arrivalLocation,
                    DateUtils.buildDate(year, month, day, hour, minutes),
                    hoursDurationArrival,
                    minutesDurationArrival);
        } else {
    
            flight = new Flight(id,
                    plane,
                    departureLocation,
                    scaleLocation,
                    arrivalLocation,
                    DateUtils.buildDate(year, month, day, hour, minutes),
                    hoursDurationArrival,
                    minutesDurationArrival,
                    hoursDurationScale,
                    minutesDurationScale);
        }

        boolean added = StorageFlights.getInstance().add(flight);
        if (!added) {
            throw new IllegalStateException("Failed to add flight to storage. Controller should have checked for existence.");
        }
        return flight;
    }

    public Flight getFlight(String id) {
     
            return StorageFlights.getInstance().get(id) ;
        
        
    }

    public List<String> allFlights() {
        List<String> ids = StorageFlights.getInstance()
                .getAll().stream()
                .map(Flight::getId)            
                .toList();
       return ValidationUtils.sortList(ids, 3, 3);
}
    
    public void addPassenger(String id,Passenger passenger){
                 StorageFlights.getInstance().get(id).addPassenger(passenger);

    }
    
    public void delay(String id, String hours, String minutes){
           Flight flight = StorageFlights.getInstance().get(id);
            
            flight.delay(Integer.parseInt(hours),Integer.parseInt(minutes));
            StorageFlights.getInstance().update(flight);
    }
    
    public ArrayList<Object[]> completeInfo(){
          ArrayList<Object[]> rows = new ArrayList<>();
   for (Flight f : StorageFlights.getInstance().getAll()) {
        Object[] row = new Object[] {
            f.getId(), 
            f.getDepartureLocation().getAirportId(),
            f.getArrivalLocation().getAirportId(), 
            (f.getScaleLocation() == null ? "-" : f.getScaleLocation().getAirportId()), 
            f.getDepartureDate(), 
            f.calculateArrivalDate(), 
            f.getPlane().getId(), 
            f.getNumPassengers()
        };
             rows.add(row);
        }
        return rows;
                
        }
    
         public void addObserver(Observer observer){
        StorageFlights.getInstance().addObserver(observer);
    }
}
    

