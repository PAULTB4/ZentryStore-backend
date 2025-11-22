package com.zentry.zentrystore.domain.messaging.event;

import java.time.LocalDateTime;

public class MessageSentEvent {

    private final Long messageId;
    private final Long conversationId;
    private final Long senderId;
    private final Long recipientId;
    private final String content;
    private final LocalDateTime occurredOn;

    public MessageSentEvent(Long messageId, Long conversationId, Long senderId,
                            Long recipientId, String content) {
        this.messageId = messageId;
        this.conversationId = conversationId;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.content = content;
        this.occurredOn = LocalDateTime.now();
    }

    public Long getMessageId() {
        return messageId;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getOccurredOn() {
        return occurredOn;
    }

    @Override
    public String toString() {
        return "MessageSentEvent{" +
                "messageId=" + messageId +
                ", conversationId=" + conversationId +
                ", senderId=" + senderId +
                ", recipientId=" + recipientId +
                ", content='" + content + '\'' +
                ", occurredOn=" + occurredOn +
                '}';
    }
}