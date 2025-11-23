package com.zentry.zentrystore.application.publication.query;

public class GetRecentPublicationsQuery {

    private final int limit;

    public GetRecentPublicationsQuery(int limit) {
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }
}
