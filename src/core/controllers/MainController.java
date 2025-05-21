/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

/**
 *
 * @author Alexander Sanguino
 */
public class MainController {
    private final JsonReaderController readerController;
    private final LocationController locContr;
    private UserController usContr;
    private PassengerController passContr;
    public MainController() {
        this.readerController = new JsonReaderController();
        this.locContr = new LocationController();
        this.usContr = new UserController();
        this.passContr = new PassengerController();
    }

    public void initializeData() {
        readerController.loadAll();
    }
    //CAMBIAR ESTO PORQUE DEBE RETORNAR ES EL CONTROLLER QUE PIDA Y SE EJECUTA EL MÉTODO DESDE ÉL:
    public void createNewLocation(String id, String name, String city, String country, String latitude, String longitude){
        locContr.createAirport(id, name, city, country, latitude, longitude);
    }
    //GETERS DE LOS CONTROLADORES:

    public UserController getUserController() {
        return usContr;
    }

    public PassengerController getPassengerController() {
        return passContr;
    }
    
}
