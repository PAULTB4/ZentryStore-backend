package com.zentry.zentrystore.domain.favorite.model;

import com.zentry.zentrystore.domain.publication.model.Publication;
import com.zentry.zentrystore.domain.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "favorites", indexes = {
        @Index(name = "idx_favorite_user", columnList = "user_id"),
        @Index(name = "idx_favorite_publication", columnList = "publication_id"),
        @Index(name = "idx_favorite_created", columnList = "created_at")
}, uniqueConstraints = {
        @UniqueConstraint(name = "uk_favorite_user_publication", columnNames = {"user_id", "publication_id"})
})
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull(message = "Publication is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publication_id", nullable = false)
    private Publication publication;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "notification_enabled")
    private Boolean notificationEnabled = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Constructors
    public Favorite() {
    }

    public Favorite(User user, Publication publication) {
        this.user = user;
        this.publication = publication;
    }

    public Favorite(User user, Publication publication, String notes) {
        this.user = user;
        this.publication = publication;
        this.notes = notes;
    }

    // Business methods
    public void enableNotifications() {
        this.notificationEnabled = true;
    }

    public void disableNotifications() {
        this.notificationEnabled = false;
    }

    public boolean hasNotificationsEnabled() {
        return Boolean.TRUE.equals(this.notificationEnabled);
    }

    public void addNotes(String notes) {
        this.notes = notes;
    }

    public void clearNotes() {
        this.notes = null;
    }

    public boolean hasNotes() {
        return this.notes != null && !this.notes.trim().isEmpty();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getNotificationEnabled() {
        return notificationEnabled;
    }

    public void setNotificationEnabled(Boolean notificationEnabled) {
        this.notificationEnabled = notificationEnabled;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}