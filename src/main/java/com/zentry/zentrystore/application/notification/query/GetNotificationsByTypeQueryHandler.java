package com.zentry.zentrystore.application.notification.query;

import com.zentry.zentrystore.application.notification.dto.NotificationDTO;
import com.zentry.zentrystore.domain.notification.model.Notification;
import com.zentry.zentrystore.domain.notification.model.NotificationType;
import com.zentry.zentrystore.domain.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetNotificationsByTypeQueryHandler {

    private final NotificationRepository notificationRepository;

    public GetNotificationsByTypeQueryHandler(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<NotificationDTO> handle(GetNotificationsByTypeQuery query) {
        NotificationType type;
        try {
            type = NotificationType.valueOf(query.getType());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid notification type: " + query.getType());
        }

        List<Notification> notifications = notificationRepository
                .findByUserIdAndTypeOrderByCreatedAtDesc(query.getUserId(), type);

        // TODO: Mapear a DTOs cuando tengamos mapper
        return null;
    }
}