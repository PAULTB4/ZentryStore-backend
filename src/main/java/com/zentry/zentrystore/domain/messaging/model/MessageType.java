package com.zentry.zentrystore.domain.messaging.model;

public enum MessageType {
    TEXT("Text"),
    IMAGE("Image"),
    OFFER("Offer"),
    SYSTEM("System");

    private final String displayName;

    MessageType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isText() {
        return this == TEXT;
    }

    public boolean isImage() {
        return this == IMAGE;
    }

    public boolean isOffer() {
        return this == OFFER;
    }

    public boolean isSystem() {
        return this == SYSTEM;
    }
}