/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alexander Sanguino
 */
public class UserController {
    public enum UserRole {
        ADMINISTRATOR,
        USER
    }
    public Response setUserRole(UserRole role, int totalTabs) {
        List<Boolean> tabStates = new ArrayList<>();

        // Inicializar todas las pestañas como deshabilitadas por defecto
        for (int i = 0; i < totalTabs; i++) {
            tabStates.add(false);
        }

        if (role == UserRole.ADMINISTRATOR) {
            // Administrator debe tener acceso a todas las pestañas a excepción de Update,
            // Add to Flight y Show my Flights.
            for (int i = 0; i < totalTabs; i++) {
                tabStates.set(i, true); // Habilitar todas por defecto
            }
            // Deshabilitar las específicas para el administrador
            if (5 < totalTabs) tabStates.set(5, false); // Update Info
            if (6 < totalTabs) tabStates.set(6, false); // Add to Flight
            if (11 < totalTabs) tabStates.set(7, false); // Show my Flights 
        } else if (role == UserRole.USER) {
            // User debe tener acceso a las pestañas de Show all Flights (índice 9),
            // Show all Locations (índice 11), Update Info (índice 5),
            // Add to Flight (índice 6) y Show my Flights (índice 7)
            if (9 < totalTabs) tabStates.set(9, true); // Show all Flights
            if (7 < totalTabs) tabStates.set(11, true); // Show all Locations
            if (5 < totalTabs) tabStates.set(5, true); // Update Info
            if (6 < totalTabs) tabStates.set(6, true); // Add to Flight
            if (11 < totalTabs) tabStates.set(7, true); // Show my Flights
        }

        return new Response("Current tab states updated.", Status.OK, tabStates); 
    }
}
