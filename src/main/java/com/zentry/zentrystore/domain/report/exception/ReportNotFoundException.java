package com.zentry.zentrystore.domain.report.exception;

public class ReportNotFoundException extends RuntimeException {

    public ReportNotFoundException(String message) {
        super(message);
    }

    public ReportNotFoundException(Long reportId) {
        super("Report not found with id: " + reportId);
    }

    public static ReportNotFoundException byId(Long id) {
        return new ReportNotFoundException(id);
    }

    public static ReportNotFoundException forEntity(Long entityId, String entityType) {
        return new ReportNotFoundException(
                String.format("No reports found for %s with id: %d", entityType, entityId)
        );
    }

    public static ReportNotFoundException forUser(Long userId) {
        return new ReportNotFoundException(
                String.format("No reports found for user with id: %d", userId)
        );
    }
}
