package com.zentry.zentrystore.application.favorite.command;

public class AddFavoriteCommand {

    private final Long userId;
    private final Long publicationId;

    public AddFavoriteCommand(Long userId, Long publicationId) {
        this.userId = userId;
        this.publicationId = publicationId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getPublicationId() {
        return publicationId;
    }
}