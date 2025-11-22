package com.zentry.zentrystore.application.notification.query;

import com.zentry.zentrystore.application.notification.dto.NotificationDTO;
import com.zentry.zentrystore.domain.notification.model.Notification;
import com.zentry.zentrystore.domain.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetUnreadNotificationsQueryHandler {

    private final NotificationRepository notificationRepository;

    public GetUnreadNotificationsQueryHandler(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<NotificationDTO> handle(GetUnreadNotificationsQuery query) {
        List<Notification> notifications = notificationRepository
                .findByUserIdAndIsReadFalseOrderByCreatedAtDesc(query.getUserId());

        // TODO: Mapear a DTOs cuando tengamos mapper
        return null;
    }
}