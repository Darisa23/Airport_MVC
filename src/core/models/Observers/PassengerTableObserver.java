/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.Observers;

import core.models.Passenger;
import core.models.storage.StoragePassengers;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Alexander Sanguino
 */
public class PassengerTableObserver implements Observer {
     private final JTable table;

    public PassengerTableObserver(JTable table) {
        this.table = table;
    }
    @Override
    public void update() {
       ArrayList<Passenger> passengers = StoragePassengers.getInstance().getAll();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (Passenger passenger : passengers) {
            model.addRow(new Object[]{passenger.getId(), passenger.getFullname(), passenger.getBirthDate(), passenger.calculateAge(), 
                passenger.generateFullPhone(), passenger.getCountry(), passenger.getNumFlights()});
        }
    }
    
}
