package com.zentry.zentrystore.application.notification.query;

import com.zentry.zentrystore.application.notification.dto.NotificationDTO;
import com.zentry.zentrystore.domain.notification.model.Notification;
import com.zentry.zentrystore.domain.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetUserNotificationsQueryHandler {

    private final NotificationRepository notificationRepository;

    public GetUserNotificationsQueryHandler(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<NotificationDTO> handle(GetUserNotificationsQuery query) {
        List<Notification> notifications;

        if (query.getLimit() != null && query.getLimit() > 0) {
            notifications = notificationRepository.findRecentByUserId(
                    query.getUserId(),
                    query.getLimit()
            );
        } else {
            notifications = notificationRepository.findByUserIdOrderByCreatedAtDesc(
                    query.getUserId()
            );
        }

        // TODO: Mapear a DTOs cuando tengamos mapper
        return null;
    }
}