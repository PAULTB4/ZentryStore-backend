package com.zentry.zentrystore.application.rating.query;

public class GetRatingsByPublicationQuery {

    private final Long publicationId;

    public GetRatingsByPublicationQuery(Long publicationId) {
        this.publicationId = publicationId;
    }

    public Long getPublicationId() {
        return publicationId;
    }
}