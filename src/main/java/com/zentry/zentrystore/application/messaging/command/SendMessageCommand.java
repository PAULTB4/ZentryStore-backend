package com.zentry.zentrystore.application.messaging.command;

public class SendMessageCommand {

    private final Long senderId;
    private final Long recipientId;
    private final String content;
    private final String attachmentUrl;
    private final String attachmentType;

    public SendMessageCommand(Long senderId, Long recipientId, String content,
                              String attachmentUrl, String attachmentType) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.content = content;
        this.attachmentUrl = attachmentUrl;
        this.attachmentType = attachmentType;
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

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public String getAttachmentType() {
        return attachmentType;
    }
}