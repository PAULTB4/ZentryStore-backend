package com.zentry.zentrystore.application.report.commad;

public class CreateReportCommand {

    private final Long reporterId;
    private final String reportType;
    private final String reportedEntityType;
    private final Long reportedEntityId;
    private final String reason;
    private final String description;

    public CreateReportCommand(Long reporterId, String reportType,
                               String reportedEntityType, Long reportedEntityId,
                               String reason, String description) {
        this.reporterId = reporterId;
        this.reportType = reportType;
        this.reportedEntityType = reportedEntityType;
        this.reportedEntityId = reportedEntityId;
        this.reason = reason;
        this.description = description;
    }

    public Long getReporterId() {
        return reporterId;
    }

    public String getReportType() {
        return reportType;
    }

    public String getReportedEntityType() {
        return reportedEntityType;
    }

    public Long getReportedEntityId() {
        return reportedEntityId;
    }

    public String getReason() {
        return reason;
    }

    public String getDescription() {
        return description;
    }
}