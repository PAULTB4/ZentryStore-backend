package com.zentry.zentrystore.application.publication.query;

public class GetActivePublicationsQuery {

    private final int limit;

    // Constructor
    public GetActivePublicationsQuery(int limit) {
        this.limit = limit;
    }

    // Getter
    public int getLimit() {
        return limit;
    }
}