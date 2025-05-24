/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.Observers;

/**
 *
 * @author maria
 */


import core.models.Flight;
import core.models.storage.StorageFlights;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class FlightTableObserver implements Observer {
    private final JTable table;

    public FlightTableObserver(JTable table) {
        this.table = table;
    }

    @Override
    public void update() {
        ArrayList<Flight> flights = StorageFlights.getInstance().getAll();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (Flight flight : flights) {
            model.addRow(new Object[]{
                flight.getId(),
                flight.getPlane().getId(),
                flight.getDepartureLocation().getAirportCity(),
                (flight.getScaleLocation() != null ? flight.getScaleLocation().getAirportCity() : "—"),
                flight.getArrivalLocation().getAirportCity(),
                flight.getDepartureDate(),
                flight.calculateArrivalDate(),
                flight.getNumPassengers()
            });
        }
    }
}
