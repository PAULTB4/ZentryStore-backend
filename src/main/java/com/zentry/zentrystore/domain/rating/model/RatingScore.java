package com.zentry.zentrystore.domain.rating.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class RatingScore {

    @NotNull(message = "Overall score is required")
    @Min(value = 1, message = "Overall score must be at least 1")
    @Max(value = 5, message = "Overall score must be at most 5")
    @Column(name = "overall_score", nullable = false)
    private Integer overallScore;

    @Min(value = 1, message = "Communication score must be at least 1")
    @Max(value = 5, message = "Communication score must be at most 5")
    @Column(name = "communication_score")
    private Integer communicationScore;

    @Min(value = 1, message = "Product quality score must be at least 1")
    @Max(value = 5, message = "Product quality score must be at most 5")
    @Column(name = "product_quality_score")
    private Integer productQualityScore;

    @Min(value = 1, message = "Delivery score must be at least 1")
    @Max(value = 5, message = "Delivery score must be at most 5")
    @Column(name = "delivery_score")
    private Integer deliveryScore;

    @Min(value = 1, message = "Value for money score must be at least 1")
    @Max(value = 5, message = "Value for money score must be at most 5")
    @Column(name = "value_for_money_score")
    private Integer valueForMoneyScore;

    // Constructors
    public RatingScore() {
    }

    public RatingScore(Integer overallScore) {
        this.overallScore = overallScore;
    }

    public RatingScore(Integer overallScore, Integer communicationScore,
                       Integer productQualityScore, Integer deliveryScore,
                       Integer valueForMoneyScore) {
        this.overallScore = overallScore;
        this.communicationScore = communicationScore;
        this.productQualityScore = productQualityScore;
        this.deliveryScore = deliveryScore;
        this.valueForMoneyScore = valueForMoneyScore;
    }

    // Business methods
    public Double calculateAverageScore() {
        int count = 0;
        int sum = overallScore;
        count++;

        if (communicationScore != null) {
            sum += communicationScore;
            count++;
        }
        if (productQualityScore != null) {
            sum += productQualityScore;
            count++;
        }
        if (deliveryScore != null) {
            sum += deliveryScore;
            count++;
        }
        if (valueForMoneyScore != null) {
            sum += valueForMoneyScore;
            count++;
        }

        return count > 0 ? (double) sum / count : 0.0;
    }

    public boolean isComplete() {
        return overallScore != null &&
                communicationScore != null &&
                productQualityScore != null &&
                deliveryScore != null &&
                valueForMoneyScore != null;
    }

    public boolean isPositive() {
        return overallScore != null && overallScore >= 4;
    }

    public boolean isNegative() {
        return overallScore != null && overallScore <= 2;
    }

    public boolean isNeutral() {
        return overallScore != null && overallScore == 3;
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
        int count = 0;
        int total = 0;

        if (overallScore != null) {
            total += overallScore;
            count++;
        }
        if (communicationScore != null) {
            total += communicationScore;
            count++;
        }
        if (productQualityScore != null) {
            total += productQualityScore;
            count++;
        }
        if (deliveryScore != null) {
            total += deliveryScore;
            count++;
        }
        if (valueForMoneyScore != null) {
            total += valueForMoneyScore;
            count++;
        }

        return count > 0 ? (double) total / count : 0.0;
    }
}