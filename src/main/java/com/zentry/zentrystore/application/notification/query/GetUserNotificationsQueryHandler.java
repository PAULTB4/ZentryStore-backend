package com.zentry.zentrystore.application.notification.query;

import com.zentry.zentrystore.application.notification.dto.NotificationDTO;
import com.zentry.zentrystore.application.notification.mapper.NotificationMapper;
import com.zentry.zentrystore.domain.notification.model.Notification;
import com.zentry.zentrystore.domain.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetUserNotificationsQueryHandler {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    public GetUserNotificationsQueryHandler(
            NotificationRepository notificationRepository,
            NotificationMapper notificationMapper) {
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
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

        return notificationMapper.toDTOList(notifications);
    }
}