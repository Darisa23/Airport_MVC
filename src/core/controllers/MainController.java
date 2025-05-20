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

    public MainController() {
        this.readerController = new JsonReaderController();
    }

    public void initializeData() {
        readerController.loadAll();
    }
}
