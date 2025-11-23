package com.zentry.zentrystore.application.user.command;

public class DeleteUserCommand {

    private final Long userId;

    public DeleteUserCommand(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}