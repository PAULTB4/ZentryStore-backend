package com.zentry.zentrystore.application.publication.query;

public class GetPublicationsByUserQuery {

    private final Long userId;

    public GetPublicationsByUserQuery(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}