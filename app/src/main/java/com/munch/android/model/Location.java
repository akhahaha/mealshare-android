package com.munch.android.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Location model
 * Created by Alan on 8/2/2016.
 */
public class Location {
    private String address;
    private String neighborhood;
    private String city;
    private String stateCode;
    private String postalCode;
    private String countryCode;
    private Coordinates coordinates;

    public Location() {
    }

    public Location(com.yelp.clientlib.entities.Location location) {
        if (location != null) {
            if (location.address() != null && !location.address().isEmpty()) {
                this.address = location.address().iterator().next();
            } else {
                this.address = "";
            }
            if (location.neighborhoods() != null && !location.neighborhoods().isEmpty()) {
                this.neighborhood = location.neighborhoods().iterator().next();
            } else {
                this.neighborhood = "";
            }
            this.city = location.city();
            this.stateCode = location.stateCode();
            this.postalCode = location.postalCode();
            this.countryCode = location.countryCode();
            this.coordinates = new Coordinates(location.coordinate());
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @JsonIgnore
    public String getDisplayAddress() {
        return address + "\n" + neighborhood + "\n" + city + ", " + stateCode + " " + postalCode;
    }

    @Override
    public String toString() {
        return "Location{" +
                "address='" + address + '\'' +
                ", neighborhood='" + neighborhood + '\'' +
                ", city='" + city + '\'' +
                ", stateCode='" + stateCode + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", coordinates=" + coordinates +
                '}';
    }
}
