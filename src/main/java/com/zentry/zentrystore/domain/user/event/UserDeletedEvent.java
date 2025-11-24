package com.zentry.zentrystore.domain.user.event;

import java.time.LocalDateTime;

public class UserDeletedEvent {

    private final Long userId;
    private final String username;
    private final String email;
    private final String reason;
    private final LocalDateTime occurredOn;

    public UserDeletedEvent(Long userId, String username, String email) {
        this(userId, username, email, null);
    }

    public UserDeletedEvent(Long userId, String username, String email, String reason) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.reason = reason;
        this.occurredOn = LocalDateTime.now();
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getReason() {
        return reason;
    }

    public LocalDateTime getOccurredOn() {
        return occurredOn;
    }

    @Override
    public String toString() {
        return "UserDeletedEvent{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", reason='" + reason + '\'' +
                ", occurredOn=" + occurredOn +
                '}';
    }
}