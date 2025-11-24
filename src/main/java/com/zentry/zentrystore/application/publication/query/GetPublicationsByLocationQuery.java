package com.zentry.zentrystore.application.publication.query;

public class GetPublicationsByLocationQuery {

    private final String city;
    private final String state;

    public GetPublicationsByLocationQuery(String city, String state) {
        this.city = city;
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }
}