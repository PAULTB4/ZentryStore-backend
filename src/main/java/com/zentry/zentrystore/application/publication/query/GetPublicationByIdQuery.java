package com.zentry.zentrystore.application.publication.query;

public class GetPublicationByIdQuery {

    private final Long publicationId;

    public GetPublicationByIdQuery(Long publicationId) {
        this.publicationId = publicationId;
    }

    public Long getPublicationId() {
        return publicationId;
    }
}
