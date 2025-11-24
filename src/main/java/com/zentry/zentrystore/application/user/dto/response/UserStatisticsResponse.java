package com.zentry.zentrystore.application.user.dto.response;

import java.time.LocalDateTime;

public class UserStatisticsResponse {

    private Long userId;
    private String username;
    private Long totalPublications;
    private Long activePublications;
    private LocalDateTime memberSince;
    private LocalDateTime lastLoginAt;

    public UserStatisticsResponse() {}

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getTotalPublications() {
        return totalPublications;
    }

    public void setTotalPublications(Long totalPublications) {
        this.totalPublications = totalPublications;
    }

    public Long getActivePublications() {
        return activePublications;
    }

    public void setActivePublications(Long activePublications) {
        this.activePublications = activePublications;
    }

    public LocalDateTime getMemberSince() {
        return memberSince;
    }

    public void setMemberSince(LocalDateTime memberSince) {
        this.memberSince = memberSince;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }
}