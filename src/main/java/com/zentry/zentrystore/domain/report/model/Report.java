package com.zentry.zentrystore.domain.report.model;

import com.zentry.zentrystore.domain.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "reports", indexes = {
        @Index(name = "idx_report_reporter", columnList = "reporter_id"),
        @Index(name = "idx_report_reported_entity", columnList = "reported_entity_id, reported_entity_type"),
        @Index(name = "idx_report_status", columnList = "status"),
        @Index(name = "idx_report_created", columnList = "created_at")
})
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Reporter is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;

    @NotNull(message = "Reported entity ID is required")
    @Column(name = "reported_entity_id", nullable = false)
    private Long reportedEntityId;

    @NotBlank(message = "Reported entity type is required")
    @Column(name = "reported_entity_type", nullable = false, length = 50)
    private String reportedEntityType; // PUBLICATION, USER, MESSAGE, RATING, etc.

    @NotBlank(message = "Reason is required")
    @Size(max = 100, message = "Reason must not exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String reason; // SPAM, INAPPROPRIATE_CONTENT, FRAUD, HARASSMENT, etc.

    @NotBlank(message = "Description is required")
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 20)
    private String status = "PENDING"; // PENDING, UNDER_REVIEW, RESOLVED, REJECTED

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewed_by")
    private User reviewedBy;

    @Column(name = "review_notes", columnDefinition = "TEXT")
    private String reviewNotes;

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

    @Column(length = 50)
    private String priority = "NORMAL"; // LOW, NORMAL, HIGH, URGENT

    @Column(name = "action_taken", columnDefinition = "TEXT")
    private String actionTaken;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public Report() {
    }

    public Report(User reporter, Long reportedEntityId, String reportedEntityType,
                  String reason, String description) {
        this.reporter = reporter;
        this.reportedEntityId = reportedEntityId;
        this.reportedEntityType = reportedEntityType;
        this.reason = reason;
        this.description = description;
    }

    // Business methods
    public void markAsUnderReview(User reviewer) {
        this.status = "UNDER_REVIEW";
        this.reviewedBy = reviewer;
        this.reviewedAt = LocalDateTime.now();
    }

    public void resolve(User reviewer, String notes, String actionTaken) {
        this.status = "RESOLVED";
        this.reviewedBy = reviewer;
        this.reviewNotes = notes;
        this.actionTaken = actionTaken;
        this.reviewedAt = LocalDateTime.now();
    }

    public void reject(User reviewer, String notes) {
        this.status = "REJECTED";
        this.reviewedBy = reviewer;
        this.reviewNotes = notes;
        this.reviewedAt = LocalDateTime.now();
    }

    public void setAsHighPriority() {
        this.priority = "HIGH";
    }

    public void setAsUrgent() {
        this.priority = "URGENT";
    }

    public boolean isPending() {
        return "PENDING".equals(this.status);
    }

    public boolean isUnderReview() {
        return "UNDER_REVIEW".equals(this.status);
    }

    public boolean isResolved() {
        return "RESOLVED".equals(this.status);
    }

    public boolean isRejected() {
        return "REJECTED".equals(this.status);
    }

    public boolean isHighPriority() {
        return "HIGH".equals(this.priority) || "URGENT".equals(this.priority);
    }

    public boolean isUrgent() {
        return "URGENT".equals(this.priority);
    }

    public boolean hasBeenReviewed() {
        return this.reviewedBy != null && this.reviewedAt != null;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getReporter() {
        return reporter;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
    }

    public Long getReportedEntityId() {
        return reportedEntityId;
    }

    public void setReportedEntityId(Long reportedEntityId) {
        this.reportedEntityId = reportedEntityId;
    }

    public String getReportedEntityType() {
        return reportedEntityType;
    }

    public void setReportedEntityType(String reportedEntityType) {
        this.reportedEntityType = reportedEntityType;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(User reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public String getReviewNotes() {
        return reviewNotes;
    }

    public void setReviewNotes(String reviewNotes) {
        this.reviewNotes = reviewNotes;
    }

    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(LocalDateTime reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getActionTaken() {
        return actionTaken;
    }

    public void setActionTaken(String actionTaken) {
        this.actionTaken = actionTaken;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}