package com.zentry.zentrystore.application.messaging.dto;

import jakarta.validation.constraints.NotNull;

public class CreateConversationRequest {

    @NotNull(message = "Current user ID is required")
    private Long currentUserId;  // ← AGREGAR ESTO

    @NotNull(message = "Recipient ID is required")
    private Long recipientId;

    private Long publicationId;

    @NotNull(message = "Initial message is required")
    private String initialMessage;

    // Constructors
    public CreateConversationRequest() {
    }

    // Getters and Setters
    public Long getCurrentUserId() {  // ← AGREGAR ESTO
        return currentUserId;
    }

    public void setCurrentUserId(Long currentUserId) {  // ← AGREGAR ESTO
        this.currentUserId = currentUserId;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public Long getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(Long publicationId) {
        this.publicationId = publicationId;
    }

    public String getInitialMessage() {
        return initialMessage;
    }

    public void setInitialMessage(String initialMessage) {
        this.initialMessage = initialMessage;
    }
}