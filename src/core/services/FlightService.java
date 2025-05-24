/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.services;

import core.controllers.FlightController;
import core.controllers.LocationController;
import core.controllers.PlaneController;
import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.controllers.utils.validators.DateUtils;
import core.controllers.utils.validators.FlightValidator;
import core.models.Flight;
import core.models.Location;
import core.models.Plane;
import core.models.storage.StorageFlights;

/**
 *
 * @author maria
 */
// FlightService.java
public class FlightService {

    // registerFlight recibe los Strings de los campos de fecha/hora/duración (DateUtils los maneja),
    // Y los objetos Plane y Location ya resueltos por el controlador.
    public Flight registerFlight(String id, Plane plane, Location departureLocation, Location arrivalLocation, Location scaleLocation,
                                  String year, String month, String day, String hour, String minutes,
                                  int hoursDurationArrival, int minutesDurationArrival,
                                  int hoursDurationScale, int minutesDurationScale) {
        Flight flight;

        // Construcción del objeto Flight, usando los objetos Plane y Location directamente,
        // y los int de duración.
        if (scaleLocation == null) {
            System.out.println("holiiii"); // Mensaje de log de tu original
            flight = new Flight(id,
                    plane,
                    departureLocation,
                    arrivalLocation,
                    DateUtils.buildDate(year, month, day, hour, minutes),
                    hoursDurationArrival,
                    minutesDurationArrival);
        } else {
            System.out.println("HOLA MARIA"); // Mensaje de log de tu original
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

    // getFlight simplemente recupera, sin validaciones de entrada.
    public Flight getFlight(String id) {
        return StorageFlights.getInstance().get(id);
    }
}