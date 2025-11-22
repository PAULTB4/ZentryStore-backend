package com.zentry.zentrystore.application.user.query;

public class GetUserByEmailQuery {

    private final String email;

    public GetUserByEmailQuery(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}