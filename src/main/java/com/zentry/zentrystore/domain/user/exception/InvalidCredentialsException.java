package com.zentry.zentrystore.domain.user.exception;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super("Invalid username or password");
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }

    public static InvalidCredentialsException withDefaultMessage() {
        return new InvalidCredentialsException();
    }

    public static InvalidCredentialsException forExpiredToken() {
        return new InvalidCredentialsException("Token has expired");
    }

    public static InvalidCredentialsException forInvalidToken() {
        return new InvalidCredentialsException("Invalid or expired token");
    }

    public static InvalidCredentialsException forAccountLocked() {
        return new InvalidCredentialsException("Account is locked or deactivated");
    }

    public static InvalidCredentialsException forEmailNotVerified() {
        return new InvalidCredentialsException("Email address has not been verified");
    }
}