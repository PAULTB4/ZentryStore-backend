package com.zentry.zentrystore.domain.notification.model;

public enum NotificationType {
    NEW_MESSAGE("New Message", "message"),
    PUBLICATION_COMMENT("Publication Comment", "comment"),
    PUBLICATION_FAVORITE("Publication Favorited", "favorite"),
    PUBLICATION_SOLD("Publication Sold", "sold"),
    NEW_RATING("New Rating Received", "rating"),
    RATING_RESPONSE("Rating Response", "rating"),
    PUBLICATION_EXPIRED("Publication Expired", "expired"),
    PUBLICATION_APPROVED("Publication Approved", "approved"),
    PUBLICATION_REJECTED("Publication Rejected", "rejected"),
    SYSTEM_ANNOUNCEMENT("System Announcement", "announcement"),
    ACCOUNT_VERIFICATION("Account Verification", "verification"),
    PASSWORD_RESET("Password Reset", "security"),
    ACCOUNT_SUSPENDED("Account Suspended", "warning"),
    REPORT_RESOLVED("Report Resolved", "report"),
    PRICE_DROP("Price Drop Alert", "price");

    private final String displayName;
    private final String icon;

    NotificationType(String displayName, String icon) {
        this.displayName = displayName;
        this.icon = icon;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getIcon() {
        return icon;
    }

    public boolean isMessageRelated() {
        return this == NEW_MESSAGE;
    }

    public boolean isPublicationRelated() {
        return this == PUBLICATION_COMMENT || this == PUBLICATION_FAVORITE ||
                this == PUBLICATION_SOLD || this == PUBLICATION_EXPIRED ||
                this == PUBLICATION_APPROVED || this == PUBLICATION_REJECTED ||
                this == PRICE_DROP;
    }

    public boolean isRatingRelated() {
        return this == NEW_RATING || this == RATING_RESPONSE;
    }

    public boolean isSecurityRelated() {
        return this == ACCOUNT_VERIFICATION || this == PASSWORD_RESET ||
                this == ACCOUNT_SUSPENDED;
    }

    public boolean isSystemRelated() {
        return this == SYSTEM_ANNOUNCEMENT;
    }

    public boolean requiresAction() {
        return this == ACCOUNT_VERIFICATION || this == PASSWORD_RESET ||
                this == PUBLICATION_EXPIRED;
    }
}