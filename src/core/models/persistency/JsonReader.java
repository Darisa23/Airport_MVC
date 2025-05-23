/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package core.models.persistency;

import java.util.List;

/**
 *
 * @author Alexander Sanguino
 * @param <T>
 */
public interface JsonReader<T> {
    void read(String path);
}
