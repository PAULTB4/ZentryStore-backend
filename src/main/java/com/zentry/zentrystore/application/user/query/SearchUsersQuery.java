package com.zentry.zentrystore.application.user.query;


public class SearchUsersQuery {

    private final String searchTerm;

    public SearchUsersQuery(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public String getSearchTerm() {
        return searchTerm;
    }
}