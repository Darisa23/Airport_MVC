/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.Observers;


import core.models.Flight;
import core.models.storage.StoragePassengers;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Alexander Sanguino
 */
public class MyFlightsObserver implements Observer {
   private final String passengerId;
    private final JTable table;
 public MyFlightsObserver(JTable table,String id){
     this.table = table;
     this.passengerId = id;
 }
    @Override
    public void update() {       
        ArrayList<Flight> flights = (ArrayList<Flight>) StoragePassengers.getInstance().get(Long.valueOf(passengerId)).getFlights().stream()
            .sorted(Comparator.comparing(Flight::getDepartureDate))
            .collect(Collectors.toList());
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
            for (Flight flight : flights) {
            model.addRow(new Object[]{
                flight.getId(),               
                flight.getDepartureDate(),
                flight.calculateArrivalDate(),
            });
        }     
    }
    
}
