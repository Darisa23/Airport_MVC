/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.models.persistency.ReadFlights;
import core.models.persistency.ReadLocations;
import core.models.persistency.ReadPassengers;
import core.models.persistency.ReadPlanes;

/**
 *
 * @author Alexander Sanguino
 */
public class JsonReaderController {
     public void loadAll() {
        new ReadLocations().read("json/locations.json");
        new ReadPassengers().read("json/passengers.json");
        new ReadPlanes().read("json/planes.json");
        new ReadFlights().read("json/flights.json");
    }
}
