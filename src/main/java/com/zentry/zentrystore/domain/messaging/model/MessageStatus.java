package com.zentry.zentrystore.domain.messaging.model;

public enum MessageStatus {
    SENT("Sent"),
    DELIVERED("Delivered"),
    READ("Read"),
    FAILED("Failed");

    private final String displayName;

    MessageStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isDelivered() {
        return this == DELIVERED || this == READ;
    }

    public boolean isRead() {
        return this == READ;
    }

    public boolean isFailed() {
        return this == FAILED;
    }
}