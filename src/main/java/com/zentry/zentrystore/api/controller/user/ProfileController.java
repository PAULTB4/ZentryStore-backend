package com.zentry.zentrystore.api.controller.user;

import com.zentry.zentrystore.application.user.command.UpdateUserProfileCommand;
import com.zentry.zentrystore.application.user.command.UpdateUserProfileCommandHandler;
import com.zentry.zentrystore.application.user.dto.request.UpdateProfileRequest;
import com.zentry.zentrystore.application.user.dto.response.UserProfileResponse;
import com.zentry.zentrystore.application.user.dto.response.UserResponse;
import com.zentry.zentrystore.application.user.query.GetUserProfileQuery;
import com.zentry.zentrystore.application.user.query.GetUserProfileQueryHandler;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/{userId}/profile")
public class ProfileController {

    private final GetUserProfileQueryHandler getUserProfileQueryHandler;
    private final UpdateUserProfileCommandHandler updateUserProfileCommandHandler;

    public ProfileController(
            GetUserProfileQueryHandler getUserProfileQueryHandler,
            UpdateUserProfileCommandHandler updateUserProfileCommandHandler) {
        this.getUserProfileQueryHandler = getUserProfileQueryHandler;
        this.updateUserProfileCommandHandler = updateUserProfileCommandHandler;
    }

    // ✅ Cualquier usuario autenticado puede ver perfiles
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<UserProfileResponse> getProfile(@PathVariable Long userId) {
        GetUserProfileQuery query = new GetUserProfileQuery(userId);
        UserProfileResponse response = getUserProfileQueryHandler.handle(query);
        return ResponseEntity.ok(response);
    }

    // ✅ Solo usuario autenticado (validación de ownership en Handler)
    @PreAuthorize("isAuthenticated()")
    @PutMapping
    public ResponseEntity<UserResponse> updateProfile(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateProfileRequest request) {
        UpdateUserProfileCommand command = new UpdateUserProfileCommand(
                userId,
                request.getFirstName(),
                request.getLastName(),
                request.getPhoneNumber(),
                request.getDateOfBirth(),
                request.getGender(),
                request.getProfilePictureUrl(),
                request.getBio(),
                request.getCity(),
                request.getState(),
                request.getCountry(),
                request.getPostalCode(),
                request.getAddress()
        );

        UserResponse response = updateUserProfileCommandHandler.handle(command);
        return ResponseEntity.ok(response);
    }
}