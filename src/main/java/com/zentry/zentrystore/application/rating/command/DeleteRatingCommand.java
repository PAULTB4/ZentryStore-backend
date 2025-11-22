package com.zentry.zentrystore.application.rating.command;

public class DeleteRatingCommand {

    private final Long ratingId;
    private final Long userId;

    public DeleteRatingCommand(Long ratingId, Long userId) {
        this.ratingId = ratingId;
        this.userId = userId;
    }

    public Long getRatingId() {
        return ratingId;
    }

    public Long getUserId() {
        return userId;
    }
}