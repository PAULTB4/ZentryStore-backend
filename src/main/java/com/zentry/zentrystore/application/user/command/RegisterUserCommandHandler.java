package com.zentry.zentrystore.application.user.command;

import com.zentry.zentrystore.application.user.dto.UserDTO;
import com.zentry.zentrystore.application.user.mapper.UserMapper;
import com.zentry.zentrystore.domain.user.exception.DuplicateEmailException;
import com.zentry.zentrystore.domain.user.model.User;
import com.zentry.zentrystore.domain.user.model.UserProfile;
import com.zentry.zentrystore.domain.user.model.UserRole;
import com.zentry.zentrystore.domain.user.repository.UserRepository;
import com.zentry.zentrystore.domain.user.repository.UserRoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterUserCommandHandler {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public RegisterUserCommandHandler(UserRepository userRepository,
                                      UserRoleRepository userRoleRepository,
                                      PasswordEncoder passwordEncoder,
                                      UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserDTO handle(RegisterUserCommand command) {
        // Validar que el email no exista
        if (userRepository.existsByEmail(command.getEmail())) {
            throw DuplicateEmailException.forEmail(command.getEmail());
        }

        // Validar que el username no exista
        if (userRepository.existsByUsername(command.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + command.getUsername());
        }

        // Crear el usuario
        User user = new User(
                command.getUsername(),
                command.getEmail(),
                passwordEncoder.encode(command.getPassword())
        );

        // Asignar rol por defecto (USER)
        UserRole userRole = userRoleRepository.findUserRole()
                .orElseThrow(() -> new IllegalStateException("Default USER role not found"));
        user.addRole(userRole);

        // Crear perfil vacío
        UserProfile profile = new UserProfile(user);
        user.setProfile(profile);

        // Guardar usuario
        User savedUser = userRepository.save(user);

        // TODO: Publicar evento UserRegisteredEvent
        // TODO: Enviar email de verificación

        // Retornar DTO
        return userMapper.toDTO(savedUser);
    }
}