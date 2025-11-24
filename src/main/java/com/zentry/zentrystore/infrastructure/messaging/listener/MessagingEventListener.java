package com.zentry.zentrystore.infrastructure.messaging.listener;

import com.zentry.zentrystore.domain.messaging.event.MessageSentEvent;
import com.zentry.zentrystore.domain.messaging.event.MessageReadEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MessagingEventListener {

    private static final Logger logger = LoggerFactory.getLogger(MessagingEventListener.class);

    @Async
    @EventListener
    public void handleMessageSent(MessageSentEvent event) {
        logger.info("Message sent in conversation: {} by user {}",
                event.getConversationId(), event.getSenderId());

        // TODO: Enviar notificación push al destinatario
        // TODO: Enviar email si el usuario tiene configurado
    }

    @Async
    @EventListener
    public void handleMessageRead(MessageReadEvent event) {
        logger.info("Message read: {} in conversation {}",
                event.getMessageId(), event.getConversationId());

        // TODO: Notificar al remitente que se leyó el mensaje
    }
}