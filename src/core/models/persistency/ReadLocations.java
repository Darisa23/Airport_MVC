/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.persistency;

import core.models.Location;
import core.models.storage.StorageLocations;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Alexander Sanguino
 */
public class ReadLocations implements JsonReader<Location>{
    @Override
    public void read(String path) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(path)));
            JSONArray array = new JSONArray(content);

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);

                Location loc = new Location(
                        obj.getString("airportId"),
                        obj.getString("airportName"),
                        obj.getString("airportCity"),
                        obj.getString("airportCountry"),
                        obj.getDouble("airportLatitude"),
                        obj.getDouble("airportLongitude")
                );
                StorageLocations.getInstance().add(loc);
            }

        } catch (Exception e) {
            System.err.println("Error reading locations: " + e.getMessage());
        }
    }  
}
