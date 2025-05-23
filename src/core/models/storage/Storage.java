/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package core.models.storage;

import java.util.ArrayList;

/**
 *
 * @author Alexander Sanguino
 * @param <T>
 * @param <k>
 */
public interface Storage<T,k> {
    boolean add(T type); 
    boolean delete(T type); 
    boolean update(T type); 
    T get(k id); 
    ArrayList<T> getAll();
}
