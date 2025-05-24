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

    public Response registerFlight(String id, String planeId, String departureLocationId, String arrivalLocationId, String scaleLocationId,
                                  String year, String month, String day, String hour, String minutes,
                                  String hoursDurationArrival, String minutesDurationArrival,
                                  String hoursDurationScale, String minutesDurationScale) {
        try {
            // Validaciones iniciales
            Response invalid = FlightValidator.INSTANCE.isValid(id, planeId, departureLocationId, arrivalLocationId, scaleLocationId, year, month, day, hour, minutes,
                    hoursDurationArrival, minutesDurationArrival, hoursDurationScale, minutesDurationScale);
            if (invalid.getStatus() != Status.OK) {
                return invalid;
            }

             // 2. ¡Validación de existencia del vuelo reincorporada!
            // Es bueno verificar si ya existe antes de intentar construir el objeto y pasarlo a 'addFlight'.
            if (StorageFlights.getInstance().get(id) != null) {
                return new Response("Flight with this ID already exists", Status.BAD_REQUEST);
            }
            // Obtener objetos Plane y Location.
            Plane plane = (Plane) PlaneController.getPlane(planeId).getObject();
            Location departureLocation = (Location) LocationController.getAirport(departureLocationId).getObject();
            Location arrivalLocation = (Location) LocationController.getAirport(arrivalLocationId).getObject();

            if (plane == null || departureLocation == null || arrivalLocation == null) {
                 return new Response("Plane or locations not found for provided IDs.", Status.BAD_REQUEST);
            }

            // Crear el objeto Flight
            Flight flight;
            // Asegúrate de que la lógica de la escala sea correcta según tu UI y datos.
            // 'scaleLocation.equals("Location")' parece indicar que no hay escala.
            if (scaleLocationId != null && !scaleLocationId.trim().isEmpty() && !scaleLocationId.equals("Location")) {
                System.out.println("HOLA MARIA - Vuelo con escala");
                Location scaleLocation = (Location) LocationController.getAirport(scaleLocationId).getObject();
                if (scaleLocation == null) {
                    return new Response("Scale location not found.", Status.BAD_REQUEST);
                }
                flight = new Flight(id,
                        plane,
                        departureLocation,
                        scaleLocation,
                        arrivalLocation,
                        DateUtils.buildDate(year, month, day, hour, minutes),
                        Integer.parseInt(hoursDurationArrival),
                        Integer.parseInt(minutesDurationArrival),
                        Integer.parseInt(hoursDurationScale),
                        Integer.parseInt(minutesDurationScale));
            } else {
                System.out.println("holiiii - Vuelo directo");
                flight = new Flight(id,
                        plane,
                        departureLocation,
                        arrivalLocation,
                        DateUtils.buildDate(year, month, day, hour, minutes),
                        Integer.parseInt(hoursDurationArrival),
                        Integer.parseInt(minutesDurationArrival));
            }

            // ¡Aquí está el cambio clave! Usa tu método addFlight del FlightController
            Response addFlightResponse = FlightController.addFlight(flight);
            
            // Si el método addFlight reporta un error (por ejemplo, ya existe), lo reenviamos.
            if (addFlightResponse.getStatus() != Status.OK) {
                return addFlightResponse; // Devuelve el error de "This flight already exists"
            }

            return new Response("Flight created successfully", Status.CREATED, flight);

        } catch (Exception e) {
            return new Response("Error creating flight: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }
}