package com.zentry.zentrystore.application.user.query;


public class GetUserByUsernameQuery {

    private final String username;

    public GetUserByUsernameQuery(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}