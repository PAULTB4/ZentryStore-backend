package com.zentry.zentrystore.application.user.command;

import com.zentry.zentrystore.domain.user.exception.UserNotFoundException;
import com.zentry.zentrystore.domain.user.model.User;
import com.zentry.zentrystore.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteUserCommandHandler {

    private final UserRepository userRepository;

    public DeleteUserCommandHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void handle(DeleteUserCommand command) {
        // Buscar usuario
        User user = userRepository.findById(command.getUserId())
                .orElseThrow(() -> UserNotFoundException.byId(command.getUserId()));

        // Desactivar usuario (soft delete)
        user.deactivate();
        userRepository.save(user);

        // TODO: Publicar evento UserDeletedEvent
        // TODO: Limpiar datos relacionados si es necesario

        // Nota: En producción, considera soft delete en lugar de hard delete
    }
}