package com.zentry.zentrystore.application.report.query;

public class GetReportsByTypeQuery {

    private final String reportType;

    public GetReportsByTypeQuery(String reportType) {
        this.reportType = reportType;
    }

    public String getReportType() {
        return reportType;
    }
}