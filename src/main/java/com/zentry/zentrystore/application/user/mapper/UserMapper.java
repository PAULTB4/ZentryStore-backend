package com.zentry.zentrystore.application.user.mapper;

import com.zentry.zentrystore.application.user.dto.UserDTO;
import com.zentry.zentrystore.application.user.dto.UserProfileDTO;
import com.zentry.zentrystore.application.user.dto.response.UserResponse;
import com.zentry.zentrystore.domain.user.model.User;
import com.zentry.zentrystore.domain.user.model.UserProfile;
import com.zentry.zentrystore.domain.user.model.UserRole;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {

    // ============ TO RESPONSE (para API) ============

    public UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }

        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setActive(user.getActive());
        response.setEmailVerified(user.getEmailVerified());
        response.setCreatedAt(user.getCreatedAt());
        response.setLastLoginAt(user.getLastLoginAt());

        // Mapear roles
        if (user.getRoles() != null) {
            response.setRoles(user.getRoles().stream()
                    .map(UserRole::getName)
                    .collect(Collectors.toSet()));
        }

        return response;
    }

    // ============ TO DTO (para uso interno) ============

    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setActive(user.getActive());
        dto.setEmailVerified(user.getEmailVerified());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setLastLoginAt(user.getLastLoginAt());

        // Mapear roles
        if (user.getRoles() != null) {
            dto.setRoles(user.getRoles().stream()
                    .map(UserRole::getName)
                    .collect(Collectors.toSet()));
        }

        // Mapear perfil
        if (user.getProfile() != null) {
            dto.setProfile(toProfileDTO(user.getProfile()));
        }

        return dto;
    }

    public UserProfileDTO toProfileDTO(UserProfile profile) {
        if (profile == null) {
            return null;
        }

        UserProfileDTO dto = new UserProfileDTO();
        dto.setId(profile.getId());
        dto.setFirstName(profile.getFirstName());
        dto.setLastName(profile.getLastName());
        dto.setFullName(profile.getFullName());
        dto.setPhoneNumber(profile.getPhoneNumber());
        dto.setDateOfBirth(profile.getDateOfBirth());
        dto.setGender(profile.getGender());
        dto.setProfilePictureUrl(profile.getProfilePictureUrl());
        dto.setBio(profile.getBio());
        dto.setCity(profile.getCity());
        dto.setState(profile.getState());
        dto.setCountry(profile.getCountry());
        dto.setPostalCode(profile.getPostalCode());

        return dto;
    }
}