package com.zentry.zentrystore.application.messaging.command;

public class CreateConversationCommand {
    private final Long userId;
    private final Long recipientId;
    private final Long publicationId;
    private final String initialMessage;

    public CreateConversationCommand(Long userId, Long recipientId, Long publicationId, String initialMessage) {
        this.userId = userId;
        this.recipientId = recipientId;
        this.publicationId = publicationId;
        this.initialMessage = initialMessage;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public Long getPublicationId() {
        return publicationId;
    }

    public String getInitialMessage() {
        return initialMessage;
    }
}