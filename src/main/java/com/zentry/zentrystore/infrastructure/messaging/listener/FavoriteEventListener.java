package com.zentry.zentrystore.infrastructure.messaging.listener;

import com.zentry.zentrystore.domain.favorite.event.FavoriteAddedEvent;
import com.zentry.zentrystore.domain.favorite.event.FavoriteRemovedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class FavoriteEventListener {

    private static final Logger logger = LoggerFactory.getLogger(FavoriteEventListener.class);

    @Async
    @EventListener
    public void handleFavoriteAdded(FavoriteAddedEvent event) {
        logger.info("Favorite added: Publication {} by user {}",
                event.getPublicationId(), event.getUserId());

        // TODO: Notificar al propietario de la publicación
        // TODO: Actualizar recomendaciones del usuario
    }

    @Async
    @EventListener
    public void handleFavoriteRemoved(FavoriteRemovedEvent event) {
        logger.info("Favorite removed: Publication {} by user {}",
                event.getPublicationId(), event.getUserId());

        // TODO: Actualizar estadísticas
    }
}