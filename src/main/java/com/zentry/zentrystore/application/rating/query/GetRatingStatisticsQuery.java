package com.zentry.zentrystore.application.rating.query;

public class GetRatingStatisticsQuery {

    private final Long publicationId;

    public GetRatingStatisticsQuery(Long publicationId) {
        this.publicationId = publicationId;
    }

    public Long getPublicationId() {
        return publicationId;
    }
}