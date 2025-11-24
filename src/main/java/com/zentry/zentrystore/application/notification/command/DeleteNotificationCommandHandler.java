package com.zentry.zentrystore.application.notification.command;

import com.zentry.zentrystore.domain.notification.model.Notification;
import com.zentry.zentrystore.domain.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteNotificationCommandHandler {

    private final NotificationRepository notificationRepository;

    public DeleteNotificationCommandHandler(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Transactional
    public void handle(DeleteNotificationCommand command) {
        Notification notification = notificationRepository.findById(command.getNotificationId())
                .orElseThrow(() -> new IllegalArgumentException("Notification not found"));

        // Verificar que la notificación pertenece al usuario
        if (!notification.getUser().getId().equals(command.getUserId())) {
            throw new IllegalArgumentException("Notification does not belong to user");
        }

        // Eliminar notificación
        notificationRepository.delete(notification);
    }
}