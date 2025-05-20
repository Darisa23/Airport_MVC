/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.providers;

import core.models.Plane;
import core.models.persistency.ReadPlane;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author Alexander Sanguino
 */
public class AirPlaneProvider implements Provider<Plane, String>{
    private final Map<String, Plane> airplanesMap = new HashMap<>();
    private ArrayList<Plane> loadedAirplanes;
    
    public AirPlaneProvider(ReadPlane airplaneJsonReader, String jsonFilePath) {
        loadedAirplanes = (ArrayList<Plane>) airplaneJsonReader.read(jsonFilePath);
        if (loadedAirplanes != null) {
            for (Plane airplane : loadedAirplanes) {
                // Asumimos que Airplane tiene un método getId() que devuelve un String
                airplanesMap.put(airplane.getId(), airplane);
            }
        } else {
            System.err.println("Advertencia: No se cargaron aviones desde " + jsonFilePath);
        }
    }
    @Override
    public Plane findById(String id) {
        return airplanesMap.get(id);
    }

    public ArrayList<Plane> getLoadedAirplanes() {
        return loadedAirplanes;
    }

    
}
