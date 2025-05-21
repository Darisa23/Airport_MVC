/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.persistency;

import core.models.Flight;
import core.models.Location;
import core.models.Plane;
import core.models.storage.StorageFlights;
import core.models.storage.StorageLocations;
import core.models.storage.StoragePlanes;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Alexander Sanguino
 */
public class ReadFlights implements JsonReader<Flight> {
     @Override
    public void read(String path) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(path)));
            JSONArray array = new JSONArray(content);
            String plane, depLoc,arrLoc,scaleLoc;
            Plane airplane;
            Location departureLocation, arrivalLocation, scaleLocation;
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                plane = obj.getString("plane");
                depLoc = obj.getString("departureLocation");
                arrLoc = obj.getString("arrivalLocation");     
                scaleLoc = obj.isNull("scaleLocation") ? null : obj.getString("scaleLocation");
                airplane = StoragePlanes.getInstance().get(plane);
                departureLocation = StorageLocations.getInstance().get(depLoc);
                arrivalLocation = StorageLocations.getInstance().get(arrLoc);
                scaleLocation = StorageLocations.getInstance().get(scaleLoc);
                if (airplane == null){
                    System.err.println("Error: Airplane with ID " + airplane + " not found for flight " + obj.getString("id"));
                }else{
                    Flight fl;
                    if(scaleLocation != null){        
                    fl = new Flight(
                        obj.getString("id"),
                        airplane,departureLocation,scaleLocation,arrivalLocation,
                        LocalDateTime.parse(obj.getString("departureDate")),
                        obj.getInt("hoursDurationArrival"),
                        obj.getInt("minutesDurationArrival"),
                        obj.getInt("hoursDurationScale"),
                        obj.getInt("minutesDurationScale")
                );
                    }else{
                        fl = new Flight(
                        obj.getString("id"),
                        airplane,departureLocation,arrivalLocation,
                        LocalDateTime.parse(obj.getString("departureDate")),
                        obj.getInt("hoursDurationArrival"),
                        obj.getInt("minutesDurationArrival")
                );
                    }
                StorageFlights.getInstance().add(fl);
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading flights: " + e.getMessage());
        }
    }
}
