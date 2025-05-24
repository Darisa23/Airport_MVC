/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers.utils;

import core.models.Flight;
import core.models.Location;
import core.models.Passenger;
import core.models.Plane;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alexander Sanguino
 */
public class Response {
    private String message;
    private int status;
    private Object object;

    public Response(String message, int status) {
        this.message = message;
        this.status = status;
    }
    
    public Response(String message, int status, Object object) {
        this.message = message;
        this.status = status;
        this.object = copyObject(object);
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public Object getObject() {
        return object;
    }

    private Object copyObject(Object obj) {
        if (obj == null) return null;

        try {
            // Si es una lista, intenta copiar cada elemento
            if (obj instanceof List<?>) {
                List<?> originalList = (List<?>) obj;
                List<Object> copiedList = new ArrayList<>();

                for (Object item : originalList) {
                    copiedList.add(copySingleObject(item));
                }
                return copiedList;
            }

            // Si no es una lista, intenta copiar el objeto individual
            return copySingleObject(obj);
        } catch (Exception e) {
            throw new RuntimeException("Error copiando el objeto para la respuesta", e);
        }
    }

    private Object copySingleObject(Object obj) {
        if (obj instanceof Passenger) {
            return ((Passenger) obj).copy();
        } else if (obj instanceof Plane) {
            return ((Plane) obj).copy();
        } else if (obj instanceof Location) {
            return ((Location) obj).copy();
        } else if (obj instanceof Flight) {
            return ((Flight) obj).copy();
        } else {
            return obj; // si no se reconoce, se retorna como está
        }
    }
    
}
