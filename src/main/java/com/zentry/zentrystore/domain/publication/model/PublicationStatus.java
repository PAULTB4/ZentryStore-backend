package com.zentry.zentrystore.domain.publication.model;

public enum PublicationStatus {
    DRAFT("Draft"),
    ACTIVE("Active"),
    PAUSED("Paused"),
    INACTIVE("Inactive"),
    SOLD("Sold"),
    EXPIRED("Expired"),
    REJECTED("Rejected"),
    UNDER_REVIEW("Under Review");

    private final String displayName;

    PublicationStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isPublished() {
        return this == ACTIVE;
    }

    public boolean canBeEdited() {
        return this == DRAFT || this == PAUSED;
    }

    public boolean isFinalized() {
        return this == SOLD || this == EXPIRED || this == REJECTED;
    }
}