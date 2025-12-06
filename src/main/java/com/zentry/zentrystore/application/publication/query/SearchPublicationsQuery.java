package com.zentry.zentrystore.application.publication.query;

import java.math.BigDecimal;

public class SearchPublicationsQuery {

    private final String searchTerm;
    private final Long categoryId;
    private final BigDecimal minPrice;
    private final BigDecimal maxPrice;
    private final String city;
    private final String condition;
    private Boolean freeShipping;
    private String sortBy;
    private String order;


    public SearchPublicationsQuery(String searchTerm, Long categoryId,
                                   BigDecimal minPrice, BigDecimal maxPrice,
                                   String city, String condition, Boolean freeShipping, String sortBy, String order) {
        this.searchTerm = searchTerm;
        this.categoryId = categoryId;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.city = city;
        this.condition = condition;
        this.freeShipping = freeShipping;
        this.sortBy = sortBy;
        this.order = order;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public String getCity() {
        return city;
    }

    public String getCondition() {
        return condition;
    }

    public Boolean getFreeShipping() {
        return freeShipping;
    }

    public String getSortBy() {
        return sortBy;
    }
    public String getOrder() {
        return order;
    }
}