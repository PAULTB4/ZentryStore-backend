package com.zentry.zentrystore.application.report.commad;

public class DeleteReportCommand {

    private final Long reportId;
    private final Long moderatorId;

    public DeleteReportCommand(Long reportId, Long moderatorId) {
        this.reportId = reportId;
        this.moderatorId = moderatorId;
    }

    public Long getReportId() {
        return reportId;
    }

    public Long getModeratorId() {
        return moderatorId;
    }
}