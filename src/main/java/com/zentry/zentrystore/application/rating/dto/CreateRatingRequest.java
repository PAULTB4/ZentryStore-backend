package com.zentry.zentrystore.application.rating.dto;

import jakarta.validation.constraints.*;

public class CreateRatingRequest {

    @NotNull(message = "Publication ID is required")
    private Long publicationId;

    @NotNull(message = "Overall score is required")
    @Min(value = 1, message = "Overall score must be at least 1")
    @Max(value = 5, message = "Overall score must be at most 5")
    private Integer overallScore;

    @Min(value = 1, message = "Communication score must be at least 1")
    @Max(value = 5, message = "Communication score must be at most 5")
    private Integer communicationScore;

    @Min(value = 1, message = "Product quality score must be at least 1")
    @Max(value = 5, message = "Product quality score must be at most 5")
    private Integer productQualityScore;

    @Min(value = 1, message = "Delivery score must be at least 1")
    @Max(value = 5, message = "Delivery score must be at most 5")
    private Integer deliveryScore;

    @Min(value = 1, message = "Value for money score must be at least 1")
    @Max(value = 5, message = "Value for money score must be at most 5")
    private Integer valueForMoneyScore;

    @Size(max = 1000, message = "Comment must not exceed 1000 characters")
    private String comment;

    private Boolean isAnonymous;

    // Constructors
    public CreateRatingRequest() {
    }

    // Getters and Setters
    public Long getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(Long publicationId) {
        this.publicationId = publicationId;
    }

    public Integer getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(Integer overallScore) {
        this.overallScore = overallScore;
    }

    public Integer getCommunicationScore() {
        return communicationScore;
    }

    public void setCommunicationScore(Integer communicationScore) {
        this.communicationScore = communicationScore;
    }

    public Integer getProductQualityScore() {
        return productQualityScore;
    }

    public void setProductQualityScore(Integer productQualityScore) {
        this.productQualityScore = productQualityScore;
    }

    public Integer getDeliveryScore() {
        return deliveryScore;
    }

    public void setDeliveryScore(Integer deliveryScore) {
        this.deliveryScore = deliveryScore;
    }

    public Integer getValueForMoneyScore() {
        return valueForMoneyScore;
    }

    public void setValueForMoneyScore(Integer valueForMoneyScore) {
        this.valueForMoneyScore = valueForMoneyScore;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(Boolean isAnonymous) {
        this.isAnonymous = isAnonymous;
    }
}