/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models;

import core.models.Observers.Observable;
import core.models.Observers.Observer;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

/**
 *
 * @author edangulo
 */
public class Passenger implements Cloneable, Observable {

    private final long id;
    private String firstname;
    private String lastname;
    private LocalDate birthDate;
    private int countryPhoneCode;
    private long phone;
    private String country;
    private ArrayList<Flight> flights;
    private final ArrayList<Observer> observers = new ArrayList<>();
    
    public Passenger(long id, String firstname, String lastname, LocalDate birthDate, int countryPhoneCode, long phone, String country) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthDate = birthDate;
        this.countryPhoneCode = countryPhoneCode;
        this.phone = phone;
        this.country = country;
        this.flights = new ArrayList<>();
    }

    public Passenger copy() {
        try {
            Passenger cloned = (Passenger) super.clone();
            cloned.flights = new ArrayList<>(this.flights); 
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("No se pudo clonar el pasajero", e);
        }
    }

    public boolean addFlight(Flight flight) {
        if (!flights.contains(flight)) {
            flights.add(flight);
            notifyObservers();
            return true;
        }
        return false;
    }

    public long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public int getCountryPhoneCode() {
        return countryPhoneCode;
    }

    public long getPhone() {
        return phone;
    }

    public String getCountry() {
        return country;
    }

    public ArrayList<Flight> getFlights() {
        return flights;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setCountryPhoneCode(int countryPhoneCode) {
        this.countryPhoneCode = countryPhoneCode;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFullname() {
        return firstname + " " + lastname;
    }

    public String generateFullPhone() {
        return "+" + countryPhoneCode + " " + phone;
    }

    public int calculateAge() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public int getNumFlights() {
        return flights.size();
    }

    @Override
    public void notifyObservers() {
     for (Observer o : observers) {
            o.update();
        }
    }

    @Override
    public void addObserver(Observer observer) {
     if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }
}
