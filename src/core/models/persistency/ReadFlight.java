/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.persistency;

import core.models.Flight;
import core.models.Location;
import core.models.Plane;
import core.providers.AirPlaneProvider;
import core.providers.LocationProvider;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Alexander Sanguino
 */
public class ReadFlight implements JsonReader<Flight> {
     private final AirPlaneProvider airplaneProvider;
     private final LocationProvider airportProvider;
     public ReadFlight(AirPlaneProvider planeProv, LocationProvider locprov) { 
        this.airplaneProvider = planeProv;
        this.airportProvider = locprov;
    }
     @Override
    public List read(String path) {
       List<Flight> list = new ArrayList<>();

        try {
            String content = new String(Files.readAllBytes(Paths.get(path)));
            JSONArray array = new JSONArray(content);
            String plane, depLoc,arrLoc,scaleLoc=null;
            Plane airplane;
            Location departureLocation, arrivalLocation, scaleLocation;
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                plane = obj.getString("plane");
                depLoc = obj.getString("departureLocation");
                arrLoc = obj.getString("arrivalLocation");     
                scaleLoc = obj.isNull("scaleLocation") ? null : obj.getString("scaleLocation");
                airplane = airplaneProvider.findById(plane);
                departureLocation = airportProvider.findById(depLoc);
                arrivalLocation = airportProvider.findById(arrLoc);
                scaleLocation = airportProvider.findById(scaleLoc);
                if (airplane == null){
                    System.err.println("Error: Airplane con ID " + airplane + " no encontrado para el vuelo " + obj.getString("id"));
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
                list.add(fl);
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading flights: " + e.getMessage());
        }

        return list;
    }
}
