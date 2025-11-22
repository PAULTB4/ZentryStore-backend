package com.zentry.zentrystore.application.notification.command;

import com.zentry.zentrystore.application.notification.dto.NotificationDTO;
import com.zentry.zentrystore.domain.notification.model.Notification;
import com.zentry.zentrystore.domain.notification.model.NotificationType;
import com.zentry.zentrystore.domain.notification.repository.NotificationRepository;
import com.zentry.zentrystore.domain.user.exception.UserNotFoundException;
import com.zentry.zentrystore.domain.user.model.User;
import com.zentry.zentrystore.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateNotificationCommandHandler {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public CreateNotificationCommandHandler(NotificationRepository notificationRepository,
                                            UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public NotificationDTO handle(CreateNotificationCommand command) {
        // Validar usuario
        User user = userRepository.findById(command.getUserId())
                .orElseThrow(() -> UserNotFoundException.byId(command.getUserId()));

        // Crear notificación
        NotificationType type;
        try {
            type = NotificationType.valueOf(command.getType());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid notification type: " + command.getType());
        }

        Notification notification = new Notification(
                user,
                type,
                command.getTitle(),
                command.getMessage(),
                command.getRelatedEntityId(),
                command.getRelatedEntityType()
        );

        // Configurar campos opcionales
        if (command.getActionUrl() != null) {
            notification.setActionUrl(command.getActionUrl());
        }

        if (command.getPriority() != null) {
            notification.setPriority(command.getPriority());
        }

        notification.setIcon(type.getIcon());

        // Guardar notificación
        Notification savedNotification = notificationRepository.save(notification);

        // TODO: Publicar evento NotificationCreatedEvent
        // TODO: Enviar push notification si el usuario lo tiene habilitado

        // Retornar DTO
        return null; // TODO: Mapear cuando tengamos mapper
    }
}