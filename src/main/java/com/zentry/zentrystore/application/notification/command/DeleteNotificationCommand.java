package com.zentry.zentrystore.application.notification.command;

public class DeleteNotificationCommand {

    private final Long notificationId;
    private final Long userId;

    public DeleteNotificationCommand(Long notificationId, Long userId) {
        this.notificationId = notificationId;
        this.userId = userId;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public Long getUserId() {
        return userId;
    }
}