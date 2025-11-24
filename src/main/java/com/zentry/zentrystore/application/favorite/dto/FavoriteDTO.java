package com.zentry.zentrystore.application.favorite.dto;

import java.time.LocalDateTime;

public class FavoriteDTO {

    private Long id;
    private Long userId;
    private Long publicationId;
    private String publicationTitle;
    private String publicationImageUrl;
    private String publicationStatus;
    private Double publicationPrice;
    private String publicationCity;
    private LocalDateTime createdAt;

    // Constructors
    public FavoriteDTO() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(Long publicationId) {
        this.publicationId = publicationId;
    }

    public String getPublicationTitle() {
        return publicationTitle;
    }

    public void setPublicationTitle(String publicationTitle) {
        this.publicationTitle = publicationTitle;
    }

    public String getPublicationImageUrl() {
        return publicationImageUrl;
    }

    public void setPublicationImageUrl(String publicationImageUrl) {
        this.publicationImageUrl = publicationImageUrl;
    }

    public String getPublicationStatus() {
        return publicationStatus;
    }

    public void setPublicationStatus(String publicationStatus) {
        this.publicationStatus = publicationStatus;
    }

    public Double getPublicationPrice() {
        return publicationPrice;
    }

    public void setPublicationPrice(Double publicationPrice) {
        this.publicationPrice = publicationPrice;
    }

    public String getPublicationCity() {
        return publicationCity;
    }

    public void setPublicationCity(String publicationCity) {
        this.publicationCity = publicationCity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
