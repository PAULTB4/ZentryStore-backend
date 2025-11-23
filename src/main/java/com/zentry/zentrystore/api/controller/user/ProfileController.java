package com.zentry.zentrystore.api.controller.user;

import com.zentry.zentrystore.application.user.command.UpdateUserProfileCommand;
import com.zentry.zentrystore.application.user.command.UpdateUserProfileCommandHandler;
import com.zentry.zentrystore.application.user.dto.request.UpdateProfileRequest;
import com.zentry.zentrystore.application.user.dto.response.UserResponse;
import com.zentry.zentrystore.application.user.query.GetUserByIdQuery;
import com.zentry.zentrystore.application.user.query.GetUserByIdQueryHandler;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/{userId}/profile")
public class ProfileController {

    private final GetUserByIdQueryHandler getUserByIdQueryHandler;
    private final UpdateUserProfileCommandHandler updateUserProfileCommandHandler;

    public ProfileController(
            GetUserByIdQueryHandler getUserByIdQueryHandler,
            UpdateUserProfileCommandHandler updateUserProfileCommandHandler) {
        this.getUserByIdQueryHandler = getUserByIdQueryHandler;
        this.updateUserProfileCommandHandler = updateUserProfileCommandHandler;
    }

    @GetMapping
    public ResponseEntity<UserResponse> getProfile(@PathVariable Long userId) {
        GetUserByIdQuery query = new GetUserByIdQuery(userId);
        UserResponse response = getUserByIdQueryHandler.handle(query);
        return ResponseEntity.ok(response);
    }

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