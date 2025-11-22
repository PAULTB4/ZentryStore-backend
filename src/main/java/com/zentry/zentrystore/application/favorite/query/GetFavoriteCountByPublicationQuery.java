package com.zentry.zentrystore.application.favorite.query;

public class GetFavoriteCountByPublicationQuery {

    private final Long publicationId;

    public GetFavoriteCountByPublicationQuery(Long publicationId) {
        this.publicationId = publicationId;
    }

    public Long getPublicationId() {
        return publicationId;
    }
}