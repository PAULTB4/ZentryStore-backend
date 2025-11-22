package com.zentry.zentrystore.domain.report.event;

import java.time.LocalDateTime;

public class ReportStatusChangedEvent {

    private final Long reportId;
    private final String oldStatus;
    private final String newStatus;
    private final LocalDateTime changedAt;

    public ReportStatusChangedEvent(Long reportId, String oldStatus, String newStatus) {
        this.reportId = reportId;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.changedAt = LocalDateTime.now();
    }

    public Long getReportId() {
        return reportId;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }
}