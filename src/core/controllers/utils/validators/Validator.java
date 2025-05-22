/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package core.controllers.utils.validators;

import core.controllers.utils.Response;

/**
 *
 * @author Alexander Sanguino
 */
public interface Validator {
     abstract Response isValid(String... fields);
}
