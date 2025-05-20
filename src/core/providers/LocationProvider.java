/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.providers;

import core.models.Location;
import core.models.persistency.ReadLocation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Alexander Sanguino
 */
public class LocationProvider implements Provider<Location, String>{
    private final Map<String, Location> locationsMap = new HashMap<>();
    private ArrayList<Location> loadedLocations;
    public LocationProvider(ReadLocation locationJsonReader, String jsonFilePath) {
        loadedLocations = (ArrayList<Location>) locationJsonReader.read(jsonFilePath);
        if (loadedLocations != null) {
            for (Location airport : loadedLocations) {
                locationsMap.put(airport.getAirportId(), airport);
            }
        } else {
            System.err.println("Advertencia: No se cargaron aeropuertos desde " + jsonFilePath);
        }
    }
    @Override
    public Location findById(String id) {
        return locationsMap.get(id);
    }

    public ArrayList<Location> getLoadedLocations() {
        return loadedLocations;
    }
    
}
