package com.zentry.zentrystore.domain.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "contact_methods")
public class ContactMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank(message = "Contact type is required")
    @Column(name = "contact_type", nullable = false, length = 50)
    private String contactType; // EMAIL, PHONE, WHATSAPP, TELEGRAM, etc.

    @NotBlank(message = "Contact value is required")
    @Column(name = "contact_value", nullable = false, length = 255)
    private String contactValue;

    @Column(nullable = false)
    private Boolean verified = false;

    @Column(nullable = false)
    private Boolean preferred = false;

    @Column(name = "verification_code", length = 10)
    private String verificationCode;

    @Column(name = "verification_expires_at")
    private LocalDateTime verificationExpiresAt;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public ContactMethod() {
    }

    public ContactMethod(String contactType, String contactValue) {
        this.contactType = contactType;
        this.contactValue = contactValue;
    }

    public ContactMethod(User user, String contactType, String contactValue) {
        this.user = user;
        this.contactType = contactType;
        this.contactValue = contactValue;
    }

    // Business methods
    public void verify() {
        this.verified = true;
        this.verificationCode = null;
        this.verificationExpiresAt = null;
    }

    public void setAsPreferred() {
        this.preferred = true;
    }

    public void removePreferred() {
        this.preferred = false;
    }

    public boolean isVerificationExpired() {
        if (verificationExpiresAt == null) {
            return false;
        }
        return LocalDateTime.now().isAfter(verificationExpiresAt);
    }

    public boolean isEmail() {
        return "EMAIL".equalsIgnoreCase(this.contactType);
    }

    public boolean isPhone() {
        return "PHONE".equalsIgnoreCase(this.contactType);
    }

    public boolean isWhatsApp() {
        return "WHATSAPP".equalsIgnoreCase(this.contactType);
    }

    public boolean isTelegram() {
        return "TELEGRAM".equalsIgnoreCase(this.contactType);
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

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getContactValue() {
        return contactValue;
    }

    public void setContactValue(String contactValue) {
        this.contactValue = contactValue;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Boolean getPreferred() {
        return preferred;
    }

    public void setPreferred(Boolean preferred) {
        this.preferred = preferred;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public LocalDateTime getVerificationExpiresAt() {
        return verificationExpiresAt;
    }

    public void setVerificationExpiresAt(LocalDateTime verificationExpiresAt) {
        this.verificationExpiresAt = verificationExpiresAt;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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