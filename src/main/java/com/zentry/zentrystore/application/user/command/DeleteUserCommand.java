package com.zentry.zentrystore.application.user.command;

public class DeleteUserCommand {

    private final Long userId;
    private final String reason;

    public DeleteUserCommand(Long userId, String reason) {
        this.userId = userId;
        this.reason = reason;
    }

    public Long getUserId() {
        return userId;
    }

    public String getReason() {
        return reason;
    }
}