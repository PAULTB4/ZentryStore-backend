package com.zentry.zentrystore.domain.user.exception;

public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException(String email) {
        super("Email already exists: " + email);
    }

    public static DuplicateEmailException forEmail(String email) {
        return new DuplicateEmailException(email);
    }
}