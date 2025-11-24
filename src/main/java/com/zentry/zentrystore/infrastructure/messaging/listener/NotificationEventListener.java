package com.zentry.zentrystore.infrastructure.messaging.listener;

import com.zentry.zentrystore.domain.notification.event.NotificationCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventListener {

    private static final Logger logger = LoggerFactory.getLogger(NotificationEventListener.class);

    @Async
    @EventListener
    public void handleNotificationCreated(NotificationCreatedEvent event) {
        logger.info("Notification created for user: {}", event.getUserId());

        // TODO: Enviar push notification
        // TODO: Enviar por WebSocket si el usuario está conectado
        // TODO: Enviar email si es prioritario
    }
}