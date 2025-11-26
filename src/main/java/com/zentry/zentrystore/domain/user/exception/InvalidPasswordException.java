package com.zentry.zentrystore.domain.user.exception;

public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException(String message) {
        super(message);
    }

    public static InvalidPasswordException wrongPassword() {
        return new InvalidPasswordException("The current password is incorrect");
    }
}