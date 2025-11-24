package com.zentry.zentrystore.application.report.query;

public class GetReportsByStatusQuery {

    private final String status;

    public GetReportsByStatusQuery(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}