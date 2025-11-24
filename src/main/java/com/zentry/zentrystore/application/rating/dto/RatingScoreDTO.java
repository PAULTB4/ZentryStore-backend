package com.zentry.zentrystore.application.rating.dto;

public class RatingScoreDTO {

    private Integer overallScore;
    private Integer communicationScore;
    private Integer productQualityScore;
    private Integer deliveryScore;
    private Integer valueForMoneyScore;
    private Double averageScore;

    // Constructors
    public RatingScoreDTO() {
    }

    // Getters and Setters
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

    public Double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
    }
}