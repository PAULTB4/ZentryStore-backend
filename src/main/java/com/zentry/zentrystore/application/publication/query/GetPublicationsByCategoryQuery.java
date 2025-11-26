package com.zentry.zentrystore.application.publication.query;

public class GetPublicationsByCategoryQuery {

    private final Long categoryId;

    public GetPublicationsByCategoryQuery(Long categoryId, int limit) {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() {
        return categoryId;
    }
}