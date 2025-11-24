package com.zentry.zentrystore.infrastructure.messaging.listener;

import com.zentry.zentrystore.domain.publication.event.PublicationCreatedEvent;
import com.zentry.zentrystore.domain.publication.event.PublicationStatusChangedEvent;
import com.zentry.zentrystore.domain.publication.event.PublicationUpdatedEvent;
import com.zentry.zentrystore.domain.publication.event.PublicationViewedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PublicationEventListener {

    private static final Logger logger = LoggerFactory.getLogger(PublicationEventListener.class);

    @Async
    @EventListener
    public void handlePublicationCreated(PublicationCreatedEvent event) {
        logger.info("Publication created: {} by user {}", event.getPublicationId(), event.getUserId());

        // TODO: Notificar a seguidores
        // TODO: Indexar en búsqueda
        // TODO: Enviar a moderación si es necesario
    }

    @Async
    @EventListener
    public void handlePublicationStatusChanged(PublicationStatusChangedEvent event) {
        logger.info("Publication {} status changed from {} to {}",
                event.getPublicationId(), event.getOldStatus(), event.getNewStatus());

        // TODO: Notificar al usuario propietario
        // TODO: Actualizar índices de búsqueda
    }

    @Async
    @EventListener
    public void handlePublicationUpdated(PublicationUpdatedEvent event) {
        logger.info("Publication updated: {}", event.getPublicationId());

        // TODO: Reindexar en búsqueda
    }

    @Async
    @EventListener
    public void handlePublicationViewed(PublicationViewedEvent event) {
        logger.debug("Publication viewed: {}", event.getPublicationId());

        // TODO: Registrar analytics
        // TODO: Actualizar recomendaciones
    }
}