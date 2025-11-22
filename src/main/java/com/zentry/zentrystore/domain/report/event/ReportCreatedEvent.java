package com.zentry.zentrystore.domain.report.event;

import java.time.LocalDateTime;

public class ReportCreatedEvent {

    private final Long reportId;
    private final Long reporterId;
    private final Long reportedEntityId;
    private final String reportedEntityType;
    private final String reason;
    private final String priority;
    private final LocalDateTime occurredOn;

    public ReportCreatedEvent(Long reportId, Long reporterId, Long reportedEntityId,
                              String reportedEntityType, String reason, String priority) {
        this.reportId = reportId;
        this.reporterId = reporterId;
        this.reportedEntityId = reportedEntityId;
        this.reportedEntityType = reportedEntityType;
        this.reason = reason;
        this.priority = priority;
        this.occurredOn = LocalDateTime.now();
    }

    public ReportCreatedEvent(Long reportId, Long reporterId, Long reportedEntityId,
                              String reportedEntityType, String reason) {
        this(reportId, reporterId, reportedEntityId, reportedEntityType, reason, "NORMAL");
    }

    public Long getReportId() {
        return reportId;
    }

    public Long getReporterId() {
        return reporterId;
    }

    public Long getReportedEntityId() {
        return reportedEntityId;
    }

    public String getReportedEntityType() {
        return reportedEntityType;
    }

    public String getReason() {
        return reason;
    }

    public String getPriority() {
        return priority;
    }

    public LocalDateTime getOccurredOn() {
        return occurredOn;
    }

    @Override
    public String toString() {
        return "ReportCreatedEvent{" +
                "reportId=" + reportId +
                ", reporterId=" + reporterId +
                ", reportedEntityId=" + reportedEntityId +
                ", reportedEntityType='" + reportedEntityType + '\'' +
                ", reason='" + reason + '\'' +
                ", priority='" + priority + '\'' +
                ", occurredOn=" + occurredOn +
                '}';
    }
}