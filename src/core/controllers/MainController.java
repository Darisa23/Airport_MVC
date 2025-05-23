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
    private PlaneController plnContr;
    private FlightController fltContr;
    public MainController() {
        this.readerController = new JsonReaderController();
        this.locContr = new LocationController();
        this.usContr = new UserController();
        this.passContr = new PassengerController();
        this.plnContr = new PlaneController();
        this.fltContr = new FlightController();
    }

    public void initializeData() {
        readerController.loadAll();
    }
    //controllers getters:
    
    public UserController getUserController() {
        return usContr;
    }

    public PassengerController getPassengerController() {
        return passContr;
    }

    public PlaneController getPlaneController() {
        return plnContr;
    }

    public LocationController getLocationController() {
        return locContr;
    }
    public FlightController getFlightController(){
        return fltContr;
    }
}
