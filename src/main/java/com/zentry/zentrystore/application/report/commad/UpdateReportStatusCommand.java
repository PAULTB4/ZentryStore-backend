package com.zentry.zentrystore.application.report.commad;

public class UpdateReportStatusCommand {

    private final Long reportId;
    private final Long moderatorId;
    private final String status;
    private final String moderatorNotes;

    public UpdateReportStatusCommand(Long reportId, Long moderatorId,
                                     String status, String moderatorNotes) {
        this.reportId = reportId;
        this.moderatorId = moderatorId;
        this.status = status;
        this.moderatorNotes = moderatorNotes;
    }

    public Long getReportId() {
        return reportId;
    }

    public Long getModeratorId() {
        return moderatorId;
    }

    public String getStatus() {
        return status;
    }

    public String getModeratorNotes() {
        return moderatorNotes;
    }
}