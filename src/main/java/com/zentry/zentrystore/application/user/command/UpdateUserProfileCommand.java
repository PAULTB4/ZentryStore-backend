package com.zentry.zentrystore.application.user.command;

import java.time.LocalDate;

public class UpdateUserProfileCommand {

    private final Long userId;
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final LocalDate dateOfBirth;
    private final String gender;
    private final String profilePictureUrl;
    private final String bio;
    private final String city;
    private final String state;
    private final String country;
    private final String postalCode;
    private final String address;

    public UpdateUserProfileCommand(Long userId, String firstName, String lastName,
                                    String phoneNumber, LocalDate dateOfBirth, String gender,
                                    String profilePictureUrl, String bio, String city,
                                    String state, String country, String postalCode,
                                    String address) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.profilePictureUrl = profilePictureUrl;
        this.bio = bio;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;
        this.address = address;
    }

    // Getters
    public Long getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public String getBio() {
        return bio;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getAddress() {
        return address;
    }
}