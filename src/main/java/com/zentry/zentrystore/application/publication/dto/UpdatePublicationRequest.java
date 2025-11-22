package com.zentry.zentrystore.application.publication.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public class UpdatePublicationRequest {

    @Size(min = 5, max = 200, message = "Title must be between 5 and 200 characters")
    private String title;

    @Size(min = 20, max = 5000, message = "Description must be between 20 and 5000 characters")
    private String description;

    private Long categoryId;

    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;

    @Size(max = 3, message = "Currency must be a valid 3-letter code")
    private String currency;

    private String condition;

    private Integer availableQuantity;

    private Boolean isNegotiable;

    private Boolean allowsShipping;

    // Location
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String address;
    private Double latitude;
    private Double longitude;

    // Images
    private List<String> imageUrls;

    // Constructors
    public UpdatePublicationRequest() {
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public Boolean getIsNegotiable() {
        return isNegotiable;
    }

    public void setIsNegotiable(Boolean isNegotiable) {
        this.isNegotiable = isNegotiable;
    }

    public Boolean getAllowsShipping() {
        return allowsShipping;
    }

    public void setAllowsShipping(Boolean allowsShipping) {
        this.allowsShipping = allowsShipping;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}