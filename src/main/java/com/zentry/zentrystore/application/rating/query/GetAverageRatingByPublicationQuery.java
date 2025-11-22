package com.zentry.zentrystore.application.rating.query;

public class GetAverageRatingByPublicationQuery {

    private final Long publicationId;

    public GetAverageRatingByPublicationQuery(Long publicationId) {
        this.publicationId = publicationId;
    }

    public Long getPublicationId() {
        return publicationId;
    }
}