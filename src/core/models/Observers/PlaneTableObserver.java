/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.Observers;

/**
 *
 * @author maria
 */
import core.models.Plane;
import core.models.storage.StoragePlanes;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PlaneTableObserver implements Observer {
    private final JTable table;

    public PlaneTableObserver(JTable table) {
        this.table = table;
    }

    @Override
    public void update() {
        ArrayList<Plane> planes = StoragePlanes.getInstance().getAll();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (Plane plane : planes) {
            model.addRow(new Object[]{
                plane.getId(),
                plane.getBrand(),
                plane.getModel(),
                plane.getAirline(),
                plane.getMaxCapacity(),
                plane.getNumFlights()
            });
        }
    }
}
