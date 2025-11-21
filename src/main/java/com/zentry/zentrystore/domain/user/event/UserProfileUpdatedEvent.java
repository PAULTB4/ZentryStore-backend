package com.zentry.zentrystore.domain.user.event;

import java.time.LocalDateTime;

public class UserProfileUpdatedEvent {

    private final Long userId;
    private final Long profileId;
    private final String firstName;
    private final String lastName;
    private final LocalDateTime occurredOn;

    public UserProfileUpdatedEvent(Long userId, Long profileId, String firstName, String lastName) {
        this.userId = userId;
        this.profileId = profileId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.occurredOn = LocalDateTime.now();
    }

    public Long getUserId() {
        return userId;
    }

    public Long getProfileId() {
        return profileId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDateTime getOccurredOn() {
        return occurredOn;
    }

    @Override
    public String toString() {
        return "UserProfileUpdatedEvent{" +
                "userId=" + userId +
                ", profileId=" + profileId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", occurredOn=" + occurredOn +
                '}';
    }
}