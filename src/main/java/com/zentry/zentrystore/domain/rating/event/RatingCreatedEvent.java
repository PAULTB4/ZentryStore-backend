package com.zentry.zentrystore.domain.rating.event;

import java.time.LocalDateTime;

public class RatingCreatedEvent {

    private final Long ratingId;
    private final Long raterId;
    private final Long ratedUserId;
    private final Long publicationId;
    private final Integer overallScore;
    private final String comment;
    private final LocalDateTime occurredOn;

    public RatingCreatedEvent(Long ratingId, Long raterId, Long ratedUserId,
                              Long publicationId, Integer overallScore, String comment) {
        this.ratingId = ratingId;
        this.raterId = raterId;
        this.ratedUserId = ratedUserId;
        this.publicationId = publicationId;
        this.overallScore = overallScore;
        this.comment = comment;
        this.occurredOn = LocalDateTime.now();
    }

    public RatingCreatedEvent(Long ratingId, Long raterId, Long ratedUserId,
                              Long publicationId, Integer overallScore) {
        this(ratingId, raterId, ratedUserId, publicationId, overallScore, null);
    }

    public Long getRatingId() {
        return ratingId;
    }

    public Long getRaterId() {
        return raterId;
    }

    public Long getRatedUserId() {
        return ratedUserId;
    }

    public Long getPublicationId() {
        return publicationId;
    }

    public Integer getOverallScore() {
        return overallScore;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getOccurredOn() {
        return occurredOn;
    }

    @Override
    public String toString() {
        return "RatingCreatedEvent{" +
                "ratingId=" + ratingId +
                ", raterId=" + raterId +
                ", ratedUserId=" + ratedUserId +
                ", publicationId=" + publicationId +
                ", overallScore=" + overallScore +
                ", comment='" + comment + '\'' +
                ", occurredOn=" + occurredOn +
                '}';
    }
}