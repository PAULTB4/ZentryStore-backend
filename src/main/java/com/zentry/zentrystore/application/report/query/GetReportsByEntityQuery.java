package com.zentry.zentrystore.application.report.query;

public class GetReportsByEntityQuery {

    private final String entityType;
    private final Long entityId;

    public GetReportsByEntityQuery(String entityType, Long entityId) {
        this.entityType = entityType;
        this.entityId = entityId;
    }

    public String getEntityType() {
        return entityType;
    }

    public Long getEntityId() {
        return entityId;
    }
}