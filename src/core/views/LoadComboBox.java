/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.views;

import java.util.List;
import javax.swing.JComboBox;

/**
 *
 * @author maria
 */
public class LoadComboBox {
   public void load(JComboBox comboBox, List<String> lista){
       comboBox.removeAllItems();
        for(String s:lista){
            comboBox.addItem(s);
        }
    }
}
