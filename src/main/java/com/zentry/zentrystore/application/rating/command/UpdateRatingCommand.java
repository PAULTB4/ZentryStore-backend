package com.zentry.zentrystore.application.rating.command;

public class UpdateRatingCommand {

    private final Long ratingId;
    private final Long userId;
    private final Integer overallScore;
    private final Integer communicationScore;
    private final Integer productQualityScore;
    private final Integer deliveryScore;
    private final Integer valueForMoneyScore;
    private final String comment;

    public UpdateRatingCommand(Long ratingId, Long userId, Integer overallScore,
                               Integer communicationScore, Integer productQualityScore,
                               Integer deliveryScore, Integer valueForMoneyScore,
                               String comment) {
        this.ratingId = ratingId;
        this.userId = userId;
        this.overallScore = overallScore;
        this.communicationScore = communicationScore;
        this.productQualityScore = productQualityScore;
        this.deliveryScore = deliveryScore;
        this.valueForMoneyScore = valueForMoneyScore;
        this.comment = comment;
    }

    // Getters
    public Long getRatingId() {
        return ratingId;
    }

    public Long getUserId() {
        return userId;
    }

    public Integer getOverallScore() {
        return overallScore;
    }

    public Integer getCommunicationScore() {
        return communicationScore;
    }

    public Integer getProductQualityScore() {
        return productQualityScore;
    }

    public Integer getDeliveryScore() {
        return deliveryScore;
    }

    public Integer getValueForMoneyScore() {
        return valueForMoneyScore;
    }

    public String getComment() {
        return comment;
    }
}