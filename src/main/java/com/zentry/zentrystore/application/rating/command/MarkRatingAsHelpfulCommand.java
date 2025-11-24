package com.zentry.zentrystore.application.rating.command;

public class MarkRatingAsHelpfulCommand {

    private final Long ratingId;
    private final Long userId;

    public MarkRatingAsHelpfulCommand(Long ratingId, Long userId) {
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