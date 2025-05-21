/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers.utils;

import core.models.Plane;
import java.util.List;

/**
 *
 * @author maria
 */
public class PlaneValidationUtils {
    
    public static boolean isPlaneIdUnique(String planeId, List<Plane> existingPlanes) {
        if (existingPlanes == null) return true;
        return existingPlanes.stream().noneMatch(p -> p.getId().equals(planeId));
    }

    // Método de validación completo para un avión (ejemplo conceptual)
    public static boolean isPlaneDataValid(String id, List<Plane> existingPlanes, String... otherNonEmptyFields) {
        if (!FormatValidationUtils.isValidPlaneIdFormat(id)) return false;
        if (!isPlaneIdUnique(id, existingPlanes)) return false;
        if (GeneralValidationUtils.areAnyFieldsEmpty(otherNonEmptyFields)) return false;
        return true;
    }
}
