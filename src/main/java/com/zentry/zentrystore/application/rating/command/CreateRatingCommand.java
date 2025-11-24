package com.zentry.zentrystore.application.rating.command;

public class CreateRatingCommand {

    private final Long raterId;
    private final Long publicationId;
    private final Integer overallScore;
    private final Integer communicationScore;
    private final Integer productQualityScore;
    private final Integer deliveryScore;
    private final Integer valueForMoneyScore;
    private final String comment;
    private final Boolean isAnonymous;

    public CreateRatingCommand(Long raterId, Long publicationId, Integer overallScore,
                               Integer communicationScore, Integer productQualityScore,
                               Integer deliveryScore, Integer valueForMoneyScore,
                               String comment, Boolean isAnonymous) {
        this.raterId = raterId;
        this.publicationId = publicationId;
        this.overallScore = overallScore;
        this.communicationScore = communicationScore;
        this.productQualityScore = productQualityScore;
        this.deliveryScore = deliveryScore;
        this.valueForMoneyScore = valueForMoneyScore;
        this.comment = comment;
        this.isAnonymous = isAnonymous;
    }

    // Getters
    public Long getRaterId() {
        return raterId;
    }

    public Long getPublicationId() {
        return publicationId;
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

    public Boolean getIsAnonymous() {
        return isAnonymous;
    }
}