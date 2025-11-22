package com.zentry.zentrystore.domain.rating.model;

import com.zentry.zentrystore.domain.publication.model.Publication;
import com.zentry.zentrystore.domain.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "ratings", indexes = {
        @Index(name = "idx_rating_publication", columnList = "publication_id"),
        @Index(name = "idx_rating_rater", columnList = "rater_id"),
        @Index(name = "idx_rating_rated_user", columnList = "rated_user_id"),
        @Index(name = "idx_rating_created", columnList = "created_at")
}, uniqueConstraints = {
        @UniqueConstraint(name = "uk_rating_rater_publication", columnNames = {"rater_id", "publication_id"})
})
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Rater is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rater_id", nullable = false)
    private User rater; // Usuario que califica

    @NotNull(message = "Rated user is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rated_user_id", nullable = false)
    private User ratedUser; // Usuario calificado (dueño de la publicación)

    @NotNull(message = "Publication is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publication_id", nullable = false)
    private Publication publication;

    @Embedded
    private RatingScore score;

    @Size(max = 1000, message = "Comment must not exceed 1000 characters")
    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(name = "is_anonymous")
    private Boolean isAnonymous = false;

    @Column(name = "is_verified_purchase")
    private Boolean isVerifiedPurchase = false;

    @Column(name = "helpful_count")
    private Integer helpfulCount = 0;

    @Column(name = "reported_count")
    private Integer reportedCount = 0;

    @Column(name = "is_visible")
    private Boolean isVisible = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public Rating() {
    }

    public Rating(User rater, User ratedUser, Publication publication, RatingScore score) {
        this.rater = rater;
        this.ratedUser = ratedUser;
        this.publication = publication;
        this.score = score;
    }

    public Rating(User rater, User ratedUser, Publication publication, RatingScore score, String comment) {
        this.rater = rater;
        this.ratedUser = ratedUser;
        this.publication = publication;
        this.score = score;
        this.comment = comment;
    }

    // Business methods
    public void incrementHelpfulCount() {
        this.helpfulCount++;
    }

    public void decrementHelpfulCount() {
        if (this.helpfulCount > 0) {
            this.helpfulCount--;
        }
    }

    public void incrementReportedCount() {
        this.reportedCount++;
    }

    public void hide() {
        this.isVisible = false;
    }

    public void show() {
        this.isVisible = true;
    }

    public boolean isPositive() {
        return this.score != null && this.score.getOverallScore() >= 4;
    }

    public boolean isNegative() {
        return this.score != null && this.score.getOverallScore() <= 2;
    }

    public boolean hasComment() {
        return this.comment != null && !this.comment.trim().isEmpty();
    }

    public boolean isFromVerifiedPurchase() {
        return Boolean.TRUE.equals(this.isVerifiedPurchase);
    }

    public void markAsVerifiedPurchase() {
        this.isVerifiedPurchase = true;
    }

    public void updateScore(RatingScore newScore) {
        this.score = newScore;
    }

    public void updateComment(String newComment) {
        this.comment = newComment;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getRater() {
        return rater;
    }

    public void setRater(User rater) {
        this.rater = rater;
    }

    public User getRatedUser() {
        return ratedUser;
    }

    public void setRatedUser(User ratedUser) {
        this.ratedUser = ratedUser;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public RatingScore getScore() {
        return score;
    }

    public void setScore(RatingScore score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(Boolean isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public Boolean getIsVerifiedPurchase() {
        return isVerifiedPurchase;
    }

    public void setIsVerifiedPurchase(Boolean isVerifiedPurchase) {
        this.isVerifiedPurchase = isVerifiedPurchase;
    }

    public Integer getHelpfulCount() {
        return helpfulCount;
    }

    public void setHelpfulCount(Integer helpfulCount) {
        this.helpfulCount = helpfulCount;
    }

    public Integer getReportedCount() {
        return reportedCount;
    }

    public void setReportedCount(Integer reportedCount) {
        this.reportedCount = reportedCount;
    }

    public Boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
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