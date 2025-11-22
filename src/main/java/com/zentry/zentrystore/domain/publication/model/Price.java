package com.zentry.zentrystore.domain.publication.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Embeddable
public class Price {

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @NotNull(message = "Currency is required")
    @Column(nullable = false, length = 3)
    private String currency; // USD, EUR, PEN, etc.

    @Column(name = "original_amount", precision = 10, scale = 2)
    private BigDecimal originalAmount;

    @Column(name = "discount_percentage", precision = 5, scale = 2)
    private BigDecimal discountPercentage;

    // Constructors
    public Price() {
    }

    public Price(BigDecimal amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Price(BigDecimal amount, String currency, BigDecimal originalAmount, BigDecimal discountPercentage) {
        this.amount = amount;
        this.currency = currency;
        this.originalAmount = originalAmount;
        this.discountPercentage = discountPercentage;
    }

    // Business methods
    public boolean hasDiscount() {
        return originalAmount != null && originalAmount.compareTo(amount) > 0;
    }

    public BigDecimal getDiscountAmount() {
        if (hasDiscount()) {
            return originalAmount.subtract(amount);
        }
        return BigDecimal.ZERO;
    }

    public void applyDiscount(BigDecimal percentage) {
        if (percentage.compareTo(BigDecimal.ZERO) > 0 && percentage.compareTo(new BigDecimal("100")) < 100) {
            this.originalAmount = this.amount;
            BigDecimal discount = this.amount.multiply(percentage).divide(new BigDecimal("100"));
            this.amount = this.amount.subtract(discount);
            this.discountPercentage = percentage;
        }
    }

    public void removeDiscount() {
        if (this.originalAmount != null) {
            this.amount = this.originalAmount;
            this.originalAmount = null;
            this.discountPercentage = null;
        }
    }

    // Getters and Setters
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}