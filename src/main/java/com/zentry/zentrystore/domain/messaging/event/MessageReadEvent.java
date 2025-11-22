package com.zentry.zentrystore.domain.messaging.event;

import java.time.LocalDateTime;

public class MessageReadEvent {

    private final Long messageId;
    private final Long conversationId;
    private final Long readerId;
    private final LocalDateTime occurredOn;

    public MessageReadEvent(Long messageId, Long conversationId, Long readerId) {
        this.messageId = messageId;
        this.conversationId = conversationId;
        this.readerId = readerId;
        this.occurredOn = LocalDateTime.now();
    }

    public Long getMessageId() {
        return messageId;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public Long getReaderId() {
        return readerId;
    }

    public LocalDateTime getOccurredOn() {
        return occurredOn;
    }

    @Override
    public String toString() {
        return "MessageReadEvent{" +
                "messageId=" + messageId +
                ", conversationId=" + conversationId +
                ", readerId=" + readerId +
                ", occurredOn=" + occurredOn +
                '}';
    }
}