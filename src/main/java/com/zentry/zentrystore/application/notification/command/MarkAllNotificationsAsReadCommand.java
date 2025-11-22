package com.zentry.zentrystore.application.notification.command;

public class MarkAllNotificationsAsReadCommand {

    private final Long userId;

    public MarkAllNotificationsAsReadCommand(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}