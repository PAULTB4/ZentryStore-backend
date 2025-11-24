package com.zentry.zentrystore.application.report.query;

public class GetReportByIdQuery {

    private final Long reportId;

    public GetReportByIdQuery(Long reportId) {
        this.reportId = reportId;
    }

    public Long getReportId() {
        return reportId;
    }
}