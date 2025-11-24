package com.zentry.zentrystore.infrastructure.messaging.listener;

import com.zentry.zentrystore.domain.report.event.ReportCreatedEvent;
import com.zentry.zentrystore.domain.report.event.ReportStatusChangedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ReportEventListener {

    private static final Logger logger = LoggerFactory.getLogger(ReportEventListener.class);

    @Async
    @EventListener
    public void handleReportCreated(ReportCreatedEvent event) {
        logger.info("Report created: {} for {} with ID {}",
                event.getReportId(), event.getReportedEntityType(), event.getReportedEntityId());

        // TODO: Notificar a moderadores
        // TODO: Si hay muchos reportes del mismo item, tomar acción automática
    }

    @Async
    @EventListener
    public void handleReportStatusChanged(ReportStatusChangedEvent event) {
        logger.info("Report {} status changed to {}",
                event.getReportId(), event.getNewStatus());

        // TODO: Notificar al reportador del cambio de estado
    }
}