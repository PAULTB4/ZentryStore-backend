package com.zentry.zentrystore.application.report.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateReportRequest {

    @NotBlank(message = "Report type is required")
    private String reportType;

    @NotBlank(message = "Reported entity type is required")
    private String reportedEntityType;

    @NotNull(message = "Reported entity ID is required")
    private Long reportedEntityId;

    @NotBlank(message = "Reason is required")
    private String reason;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    // Constructors
    public CreateReportRequest() {
    }

    // Getters and Setters
    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReportedEntityType() {
        return reportedEntityType;
    }

    public void setReportedEntityType(String reportedEntityType) {
        this.reportedEntityType = reportedEntityType;
    }

    public Long getReportedEntityId() {
        return reportedEntityId;
    }

    public void setReportedEntityId(Long reportedEntityId) {
        this.reportedEntityId = reportedEntityId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
