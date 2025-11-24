package com.zentry.zentrystore.domain.user.exception;


public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Long userId) {
        super("User not found with id: " + userId);
    }

    public UserNotFoundException(String field, String value) {
        super(String.format("User not found with %s: %s", field, value));
    }

    public static UserNotFoundException byId(Long id) {
        return new UserNotFoundException(id);
    }

    public static UserNotFoundException byUsername(String username) {
        return new UserNotFoundException("username", username);
    }

    public static UserNotFoundException byEmail(String email) {
        return new UserNotFoundException("email", email);
    }

    public static UserNotFoundException byVerificationToken(String token) {
        return new UserNotFoundException("verification token", token);
    }

    public static UserNotFoundException byPasswordResetToken(String token) {
        return new UserNotFoundException("password reset token", token);
    }
}