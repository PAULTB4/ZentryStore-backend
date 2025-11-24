package com.zentry.zentrystore.application.user.query;

import com.zentry.zentrystore.application.user.dto.response.UserProfileResponse;
import com.zentry.zentrystore.domain.user.model.UserProfile;
import com.zentry.zentrystore.domain.user.repository.UserProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetUserProfileQueryHandler {

    private final UserProfileRepository userProfileRepository;

    public GetUserProfileQueryHandler(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Transactional(readOnly = true)
    public UserProfileResponse handle(GetUserProfileQuery query) {
        UserProfile profile = userProfileRepository.findByUserId(query.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Profile not found for user: " + query.getUserId()));

        return mapToResponse(profile);
    }

    private UserProfileResponse mapToResponse(UserProfile profile) {
        return new UserProfileResponse(
                profile.getId(),
                profile.getUser().getId(),
                profile.getFirstName(),
                profile.getLastName(),
                profile.getPhoneNumber(),
                profile.getDateOfBirth(),
                profile.getGender(),
                profile.getProfilePictureUrl(),
                profile.getBio(),
                profile.getCity(),
                profile.getState(),
                profile.getCountry(),
                profile.getPostalCode(),
                profile.getAddress(),
                profile.getCreatedAt(),
                profile.getUpdatedAt()
        );
    }
}