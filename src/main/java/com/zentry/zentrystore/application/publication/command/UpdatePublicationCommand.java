package com.zentry.zentrystore.application.publication.command;

import java.math.BigDecimal;
import java.util.List;

public class UpdatePublicationCommand {

    private final Long publicationId;
    private final String title;
    private final String description;
    private final Long categoryId;
    private final BigDecimal price;
    private final String currency;
    private final String condition;
    private final Integer availableQuantity;
    private final Boolean isNegotiable;
    private final Boolean allowsShipping;
    private final String city;
    private final String state;
    private final String country;
    private final String postalCode;
    private final String address;
    private final Double latitude;
    private final Double longitude;
    private final List<String> imageUrls;

    public UpdatePublicationCommand(Long publicationId, String title,
                                    String description, Long categoryId, BigDecimal price,
                                    String currency, String condition, Integer availableQuantity,
                                    Boolean isNegotiable, Boolean allowsShipping,
                                    String city, String state, String country,
                                    String postalCode, String address,
                                    Double latitude, Double longitude,
                                    List<String> imageUrls) {
        this.publicationId = publicationId;
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
        this.price = price;
        this.currency = currency;
        this.condition = condition;
        this.availableQuantity = availableQuantity;
        this.isNegotiable = isNegotiable;
        this.allowsShipping = allowsShipping;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageUrls = imageUrls;
    }

    public Long getPublicationId() { return publicationId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Long getCategoryId() { return categoryId; }
    public BigDecimal getPrice() { return price; }
    public String getCurrency() { return currency; }
    public String getCondition() { return condition; }
    public Integer getAvailableQuantity() { return availableQuantity; }
    public Boolean getIsNegotiable() { return isNegotiable; }
    public Boolean getAllowsShipping() { return allowsShipping; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getCountry() { return country; }
    public String getPostalCode() { return postalCode; }
    public String getAddress() { return address; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
    public List<String> getImageUrls() { return imageUrls; }
}