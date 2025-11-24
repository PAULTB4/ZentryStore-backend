package com.zentry.zentrystore.application.user.command;

import com.zentry.zentrystore.application.user.dto.response.UserResponse;
import com.zentry.zentrystore.application.user.mapper.UserMapper;
import com.zentry.zentrystore.domain.user.exception.UserNotFoundException;
import com.zentry.zentrystore.domain.user.model.User;
import com.zentry.zentrystore.domain.user.model.UserProfile;
import com.zentry.zentrystore.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateUserProfileCommandHandler {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UpdateUserProfileCommandHandler(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserResponse handle(UpdateUserProfileCommand command) {
        User user = userRepository.findById(command.getUserId())
                .orElseThrow(() -> new UserNotFoundException(command.getUserId()));

        UserProfile profile = user.getProfile();
        if (profile == null) {
            profile = new UserProfile(user);
            user.setProfile(profile);
        }

        // Actualizar campos del perfil
        profile.setFirstName(command.getFirstName());
        profile.setLastName(command.getLastName());
        profile.setPhoneNumber(command.getPhoneNumber());
        profile.setDateOfBirth(command.getDateOfBirth());
        profile.setGender(command.getGender());
        profile.setProfilePictureUrl(command.getProfilePictureUrl());
        profile.setBio(command.getBio());
        profile.setCity(command.getCity());
        profile.setState(command.getState());
        profile.setCountry(command.getCountry());
        profile.setPostalCode(command.getPostalCode());

        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }
}