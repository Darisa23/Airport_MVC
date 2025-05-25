/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.Observers;

/**
 *
 * @author maria
 */

import core.models.Location;
import core.models.storage.StorageLocations;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class LocationTableObserver implements Observer {
    private final JTable table;

    public LocationTableObserver(JTable table) {
        this.table = table;
    }

    @Override
    public void update() {
        ArrayList<Location> locations = (ArrayList<Location>) StorageLocations.getInstance().getAll().stream()
            .sorted(Comparator.comparing(Location::getAirportId))
            .collect(Collectors.toList());
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (Location location : locations) {
            model.addRow(new Object[]{
                location.getAirportId(),
                location.getAirportName(),
                location.getAirportCity(),
                location.getAirportCountry(),
                location.getAirportLatitude(),
                location.getAirportLongitude()
            });
        }
    }
}
