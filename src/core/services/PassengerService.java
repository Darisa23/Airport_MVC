/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.services;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.controllers.utils.validators.DateUtils;
import core.controllers.utils.validators.PassengerValidator;
import core.controllers.utils.validators.ValidationUtils;
import core.models.Passenger;
import core.models.storage.StoragePassengers;

/**
 *
 * @author maria
 */


 
public class PassengerService {


    public Passenger registerPassenger(long id, String firstName, String lastName, String year, String month, String day,
                                       int countryPhoneCode, long phone, String country) {

        Passenger passenger = new Passenger(
                id,
                firstName,
                lastName,
                DateUtils.buildDate(year, month, day),
                countryPhoneCode, 
                phone, 
                country
        );

      
        boolean added = StoragePassengers.getInstance().add(passenger);
        if (!added) {
   
          
            throw new IllegalStateException("Failed to add passenger to storage despite prior checks by controller.");
        }

        return passenger;
    }


    public Passenger getPassenger(long id) {
        return StoragePassengers.getInstance().get(id);
    }
}