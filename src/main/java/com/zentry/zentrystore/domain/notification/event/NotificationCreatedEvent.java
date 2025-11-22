package com.zentry.zentrystore.domain.notification.event;

import com.zentry.zentrystore.domain.notification.model.NotificationType;

import java.time.LocalDateTime;

public class NotificationCreatedEvent {

    private final Long notificationId;
    private final Long userId;
    private final NotificationType type;
    private final String title;
    private final String message;
    private final String priority;
    private final LocalDateTime occurredOn;

    public NotificationCreatedEvent(Long notificationId, Long userId, NotificationType type,
                                    String title, String message, String priority) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.type = type;
        this.title = title;
        this.message = message;
        this.priority = priority;
        this.occurredOn = LocalDateTime.now();
    }

    public NotificationCreatedEvent(Long notificationId, Long userId, NotificationType type,
                                    String title, String message) {
        this(notificationId, userId, type, title, message, "NORMAL");
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public Long getUserId() {
        return userId;
    }

    public NotificationType getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getPriority() {
        return priority;
    }

    public LocalDateTime getOccurredOn() {
        return occurredOn;
    }

    @Override
    public String toString() {
        return "NotificationCreatedEvent{" +
                "notificationId=" + notificationId +
                ", userId=" + userId +
                ", type=" + type +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", priority='" + priority + '\'' +
                ", occurredOn=" + occurredOn +
                '}';
    }
}