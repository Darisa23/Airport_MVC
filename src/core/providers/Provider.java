/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package core.providers;

import java.util.Optional;

/**
 *
 * @author Alexander Sanguino
 * @param <T>
 * @param <K>
 */
public interface Provider<T,K> {
    T findById(K id);
}
