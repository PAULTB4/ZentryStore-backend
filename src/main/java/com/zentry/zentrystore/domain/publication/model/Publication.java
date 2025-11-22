package com.zentry.zentrystore.domain.publication.model;

import com.zentry.zentrystore.domain.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "publications", indexes = {
        @Index(name = "idx_publication_status", columnList = "status"),
        @Index(name = "idx_publication_user", columnList = "user_id"),
        @Index(name = "idx_publication_category", columnList = "category_id"),
        @Index(name = "idx_publication_created", columnList = "created_at")
})
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 200, message = "Title must be between 5 and 200 characters")
    @Column(nullable = false, length = 200)
    private String title;

    @NotBlank(message = "Description is required")
    @Size(min = 20, max = 5000, message = "Description must be between 20 and 5000 characters")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "User is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull(message = "Category is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PublicationStatus status = PublicationStatus.DRAFT;

    @Embedded
    private Price price;

    @Embedded
    private Location location;

    @Column(length = 50)
    private String condition; // NEW, USED, LIKE_NEW, REFURBISHED

    @Column(name = "available_quantity")
    private Integer availableQuantity;

    @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images = new ArrayList<>();

    @Column(name = "view_count")
    private Long viewCount = 0L;

    @Column(name = "favorite_count")
    private Long favoriteCount = 0L;

    @Column(name = "is_featured")
    private Boolean isFeatured = false;

    @Column(name = "is_negotiable")
    private Boolean isNegotiable = true;

    @Column(name = "allows_shipping")
    private Boolean allowsShipping = false;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public Publication() {
    }

    public Publication(String title, String description, User user, Category category) {
        this.title = title;
        this.description = description;
        this.user = user;
        this.category = category;
    }

    // Business methods
    public void publish() {
        this.status = PublicationStatus.ACTIVE;
        this.publishedAt = LocalDateTime.now();
    }

    public void pause() {
        this.status = PublicationStatus.PAUSED;
    }

    public void deactivate() {
        this.status = PublicationStatus.INACTIVE;
    }

    public void markAsSold() {
        this.status = PublicationStatus.SOLD;
    }

    public void expire() {
        this.status = PublicationStatus.EXPIRED;
    }

    public void incrementViewCount() {
        this.viewCount++;
    }

    public void incrementFavoriteCount() {
        this.favoriteCount++;
    }

    public void decrementFavoriteCount() {
        if (this.favoriteCount > 0) {
            this.favoriteCount--;
        }
    }

    public void addImage(ProductImage image) {
        this.images.add(image);
        image.setPublication(this);
    }

    public void removeImage(ProductImage image) {
        this.images.remove(image);
        image.setPublication(null);
    }

    public boolean isActive() {
        return this.status == PublicationStatus.ACTIVE;
    }

    public boolean isDraft() {
        return this.status == PublicationStatus.DRAFT;
    }

    public boolean isSold() {
        return this.status == PublicationStatus.SOLD;
    }

    public boolean isExpired() {
        if (this.expiresAt != null) {
            return LocalDateTime.now().isAfter(this.expiresAt);
        }
        return false;
    }

    public boolean canBeEdited() {
        return this.status == PublicationStatus.DRAFT ||
                this.status == PublicationStatus.PAUSED;
    }

    public void setAsFeatured() {
        this.isFeatured = true;
    }

    public void removeFeatured() {
        this.isFeatured = false;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public PublicationStatus getStatus() {
        return status;
    }

    public void setStatus(PublicationStatus status) {
        this.status = status;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public List<ProductImage> getImages() {
        return images;
    }

    public void setImages(List<ProductImage> images) {
        this.images = images;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public Long getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(Long favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public Boolean getIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(Boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    public Boolean getIsNegotiable() {
        return isNegotiable;
    }

    public void setIsNegotiable(Boolean isNegotiable) {
        this.isNegotiable = isNegotiable;
    }

    public Boolean getAllowsShipping() {
        return allowsShipping;
    }

    public void setAllowsShipping(Boolean allowsShipping) {
        this.allowsShipping = allowsShipping;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
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