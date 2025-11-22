package com.zentry.zentrystore.application.notification.command;

import com.zentry.zentrystore.domain.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class MarkAllNotificationsAsReadCommandHandler {

    private final NotificationRepository notificationRepository;

    public MarkAllNotificationsAsReadCommandHandler(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Transactional
    public void handle(MarkAllNotificationsAsReadCommand command) {
        notificationRepository.markAllAsReadByUserId(
                command.getUserId(),
                LocalDateTime.now()
        );
    }
}