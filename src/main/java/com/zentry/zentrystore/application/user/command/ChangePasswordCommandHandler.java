package com.zentry.zentrystore.application.user.command;

import com.zentry.zentrystore.domain.user.exception.InvalidPasswordException;
import com.zentry.zentrystore.domain.user.exception.UserNotFoundException;
import com.zentry.zentrystore.domain.user.model.User;
import com.zentry.zentrystore.domain.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChangePasswordCommandHandler {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ChangePasswordCommandHandler(UserRepository userRepository,
                                        PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void handle(ChangePasswordCommand command) {
        User user = userRepository.findById(command.getUserId())
                .orElseThrow(() -> UserNotFoundException.byId(command.getUserId()));

        // Verificar que la contraseña actual sea correcta
        if (!passwordEncoder.matches(command.getCurrentPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Current password is incorrect");
        }

        // Encriptar y establecer la nueva contraseña
        String encodedNewPassword = passwordEncoder.encode(command.getNewPassword());
        user.setPassword(encodedNewPassword);

        userRepository.save(user);

        // TODO: Enviar email de confirmación de cambio de contraseña
    }
}