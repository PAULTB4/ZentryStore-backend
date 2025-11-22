package com.zentry.zentrystore.application.user.command;


import com.zentry.zentrystore.application.user.dto.UserDTO;
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

    public UpdateUserProfileCommandHandler(UserRepository userRepository,
                                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserDTO handle(UpdateUserProfileCommand command) {
        // Buscar usuario
        User user = userRepository.findById(command.getUserId())
                .orElseThrow(() -> UserNotFoundException.byId(command.getUserId()));

        // Obtener o crear perfil
        UserProfile profile = user.getProfile();
        if (profile == null) {
            profile = new UserProfile(user);
            user.setProfile(profile);
        }

        // Actualizar datos del perfil
        if (command.getFirstName() != null) {
            profile.setFirstName(command.getFirstName());
        }
        if (command.getLastName() != null) {
            profile.setLastName(command.getLastName());
        }
        if (command.getPhoneNumber() != null) {
            profile.setPhoneNumber(command.getPhoneNumber());
        }
        if (command.getDateOfBirth() != null) {
            profile.setDateOfBirth(command.getDateOfBirth());
        }
        if (command.getGender() != null) {
            profile.setGender(command.getGender());
        }
        if (command.getProfilePictureUrl() != null) {
            profile.setProfilePictureUrl(command.getProfilePictureUrl());
        }
        if (command.getBio() != null) {
            profile.setBio(command.getBio());
        }
        if (command.getCity() != null) {
            profile.setCity(command.getCity());
        }
        if (command.getState() != null) {
            profile.setState(command.getState());
        }
        if (command.getCountry() != null) {
            profile.setCountry(command.getCountry());
        }
        if (command.getPostalCode() != null) {
            profile.setPostalCode(command.getPostalCode());
        }
        if (command.getAddress() != null) {
            profile.setAddress(command.getAddress());
        }

        // Guardar cambios
        User savedUser = userRepository.save(user);

        // TODO: Publicar evento UserProfileUpdatedEvent

        // Retornar DTO
        return userMapper.toDTO(savedUser);
    }
}