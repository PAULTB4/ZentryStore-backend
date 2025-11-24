package com.zentry.zentrystore.infrastructure.messaging.listener;

import com.zentry.zentrystore.domain.user.event.UserRegisteredEvent;
import com.zentry.zentrystore.domain.user.event.UserProfileUpdatedEvent;
import com.zentry.zentrystore.domain.user.event.UserDeletedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class UserEventListener {

    private static final Logger logger = LoggerFactory.getLogger(UserEventListener.class);

    @Async
    @EventListener
    public void handleUserRegistered(UserRegisteredEvent event) {
        logger.info("User registered: {} - {}", event.getUserId(), event.getEmail());

        // TODO: Enviar email de bienvenida
        // TODO: Crear notificación de bienvenida
        // TODO: Registrar en analytics
    }

    @Async
    @EventListener
    public void handleUserProfileUpdated(UserProfileUpdatedEvent event) {
        logger.info("User profile updated: {}", event.getUserId());

        // TODO: Notificar cambios si es necesario
    }

    @Async
    @EventListener
    public void handleUserDeleted(UserDeletedEvent event) {
        logger.info("User deleted: {}", event.getUserId());

        // TODO: Limpiar datos relacionados
        // TODO: Enviar email de confirmación
    }
}