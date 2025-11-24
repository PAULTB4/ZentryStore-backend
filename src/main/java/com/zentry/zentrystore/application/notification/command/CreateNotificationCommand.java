package com.zentry.zentrystore.application.notification.command;

public class CreateNotificationCommand {

    private final Long userId;
    private final String type;
    private final String title;
    private final String message;
    private final Long relatedEntityId;
    private final String relatedEntityType;
    private final String actionUrl;
    private final String priority;

    public CreateNotificationCommand(Long userId, String type, String title, String message,
                                     Long relatedEntityId, String relatedEntityType,
                                     String actionUrl, String priority) {
        this.userId = userId;
        this.type = type;
        this.title = title;
        this.message = message;
        this.relatedEntityId = relatedEntityId;
        this.relatedEntityType = relatedEntityType;
        this.actionUrl = actionUrl;
        this.priority = priority;
    }

    public Long getUserId() {
        return userId;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public Long getRelatedEntityId() {
        return relatedEntityId;
    }

    public String getRelatedEntityType() {
        return relatedEntityType;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public String getPriority() {
        return priority;
    }
}