package com.zentry.zentrystore.application.messaging.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SendMessageRequest {

    @NotNull(message = "Sender ID is required")
    private Long senderId;  // ← AGREGAR ESTO

    @NotNull(message = "Recipient ID is required")
    private Long recipientId;

    @NotBlank(message = "Content is required")
    @Size(max = 5000, message = "Message content must not exceed 5000 characters")
    private String content;

    private String attachmentUrl;
    private String attachmentType;

    // Constructors
    public SendMessageRequest() {
    }

    // Getters and Setters
    public Long getSenderId() {  // ← AGREGAR ESTO
        return senderId;
    }

    public void setSenderId(Long senderId) {  // ← AGREGAR ESTO
        this.senderId = senderId;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public String getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
    }
}