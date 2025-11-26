package com.zentry.zentrystore.application.user.command;

import com.zentry.zentrystore.domain.user.exception.UserNotFoundException;
import com.zentry.zentrystore.domain.user.model.User;
import com.zentry.zentrystore.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VerifyEmailCommandHandler {

    private final UserRepository userRepository;

    public VerifyEmailCommandHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void handle(VerifyEmailCommand command) {
        User user = userRepository.findById(command.getUserId())
                .orElseThrow(() -> UserNotFoundException.byId(command.getUserId()));

        // Verificar el email
        user.setEmailVerified(true);
        user.setVerificationToken(null);

        userRepository.save(user);

        // TODO: Enviar email de bienvenida
    }
}