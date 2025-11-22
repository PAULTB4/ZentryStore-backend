package com.zentry.zentrystore.infrastructure.messaging.listener;

import com.zentry.zentrystore.domain.rating.event.RatingCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class RatingEventListener {

    private static final Logger logger = LoggerFactory.getLogger(RatingEventListener.class);

    @Async
    @EventListener
    public void handleRatingCreated(RatingCreatedEvent event) {
        logger.info("Rating created: {} by user {} for user {}",
                event.getRatingId(), event.getRaterId(), event.getRatedUserId());

        // TODO: Notificar al usuario calificado
        // TODO: Actualizar promedio de calificaciones
        // TODO: Verificar si cumple objetivos/badges
    }
}