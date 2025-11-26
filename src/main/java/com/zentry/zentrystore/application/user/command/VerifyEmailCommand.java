package com.zentry.zentrystore.application.user.command;

public class VerifyEmailCommand {

    private final Long userId;

    public VerifyEmailCommand(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}