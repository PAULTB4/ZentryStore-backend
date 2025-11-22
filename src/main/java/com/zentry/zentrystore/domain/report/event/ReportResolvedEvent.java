package com.zentry.zentrystore.domain.report.event;

import java.time.LocalDateTime;

public class ReportResolvedEvent {

    private final Long reportId;
    private final Long reporterId;
    private final Long reviewerId;
    private final Long reportedEntityId;
    private final String reportedEntityType;
    private final String status;
    private final String actionTaken;
    private final LocalDateTime occurredOn;

    public ReportResolvedEvent(Long reportId, Long reporterId, Long reviewerId,
                               Long reportedEntityId, String reportedEntityType,
                               String status, String actionTaken) {
        this.reportId = reportId;
        this.reporterId = reporterId;
        this.reviewerId = reviewerId;
        this.reportedEntityId = reportedEntityId;
        this.reportedEntityType = reportedEntityType;
        this.status = status;
        this.actionTaken = actionTaken;
        this.occurredOn = LocalDateTime.now();
    }

    public Long getReportId() {
        return reportId;
    }

    public Long getReporterId() {
        return reporterId;
    }

    public Long getReviewerId() {
        return reviewerId;
    }

    public Long getReportedEntityId() {
        return reportedEntityId;
    }

    public String getReportedEntityType() {
        return reportedEntityType;
    }

    public String getStatus() {
        return status;
    }

    public String getActionTaken() {
        return actionTaken;
    }

    public LocalDateTime getOccurredOn() {
        return occurredOn;
    }

    @Override
    public String toString() {
        return "ReportResolvedEvent{" +
                "reportId=" + reportId +
                ", reporterId=" + reporterId +
                ", reviewerId=" + reviewerId +
                ", reportedEntityId=" + reportedEntityId +
                ", reportedEntityType='" + reportedEntityType + '\'' +
                ", status='" + status + '\'' +
                ", actionTaken='" + actionTaken + '\'' +
                ", occurredOn=" + occurredOn +
                '}';
    }
}