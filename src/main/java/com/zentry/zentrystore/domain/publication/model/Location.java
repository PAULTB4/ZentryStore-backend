package com.zentry.zentrystore.domain.publication.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;

@Embeddable
public class Location {

    @Size(max = 100, message = "City must not exceed 100 characters")
    @Column(name = "city", length = 100)
    private String city;

    @Size(max = 100, message = "State must not exceed 100 characters")
    @Column(name = "state", length = 100)
    private String state;

    @Size(max = 100, message = "Country must not exceed 100 characters")
    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "postal_code", length = 20)
    private String postalCode;

    // ⚠️ CORREGIDO: Especificar nombre de columna explícitamente
    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    // Constructors
    public Location() {
    }

    public Location(String city, String country) {
        this.city = city;
        this.country = country;
    }

    public Location(String city, String state, String country) {
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public Location(String city, String state, String country, String postalCode, String address) {
        this.city = city;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;
        this.address = address;
    }

    // Business methods
    public boolean hasCoordinates() {
        return latitude != null && longitude != null;
    }

    public String getFullLocation() {
        StringBuilder fullLocation = new StringBuilder();
        if (city != null) fullLocation.append(city);
        if (state != null) {
            if (fullLocation.length() > 0) fullLocation.append(", ");
            fullLocation.append(state);
        }
        if (country != null) {
            if (fullLocation.length() > 0) fullLocation.append(", ");
            fullLocation.append(country);
        }
        return fullLocation.toString();
    }

    public boolean isComplete() {
        return city != null && country != null;
    }

    // Getters and Setters
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}