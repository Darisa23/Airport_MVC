/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.persistency;

import core.models.Plane;
import core.models.storage.StoragePlanes;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Alexander Sanguino
 */
public class ReadPlane implements JsonReader<Plane> {
   @Override
    public void read(String path) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(path)));
            JSONArray array = new JSONArray(content);

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);

                Plane pln = new Plane(
                        obj.getString("id"),
                        obj.getString("brand"),
                        obj.getString("model"),
                        obj.getInt("maxCapacity"),
                        obj.getString("airline")
                );
                StoragePlanes.getInstance().add(pln);
            }

        } catch (Exception e) {
            System.err.println("Error reading planes: " + e.getMessage());
        }
    }
    
}
