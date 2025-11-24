package com.zentry.zentrystore.domain.report.exception;

public class DuplicateReportException extends RuntimeException {

    public DuplicateReportException(String message) {
        super(message);
    }

    public static DuplicateReportException forEntity(String entityType, Long entityId) {
        return new DuplicateReportException(
                String.format("Report already exists for %s with ID: %d", entityType, entityId)
        );
    }
}