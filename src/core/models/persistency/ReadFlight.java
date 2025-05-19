/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.persistency;

import core.models.Flight;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Alexander Sanguino
 */
public class ReadFlight implements JsonReader<Flight> {
    @Override
    public List read(String path) {
       List<Flight> list = new ArrayList<>();

        try {
            String content = new String(Files.readAllBytes(Paths.get(path)));
            JSONArray array = new JSONArray(content);

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);

                /*Flight fl = new Flight(
                        obj.getString("id"),
                        obj.getString("brand"),
                        obj.getString("model"),
                        obj.getInt("maxCapacity"),
                        obj.getString("airLine")
                );
                list.add(fl);*/
            }

        } catch (Exception e) {
            System.err.println("Error reading passengers: " + e.getMessage());
        }

        return list;
    }
}
