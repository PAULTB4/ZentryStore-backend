package com.zentry.zentrystore.application.notification.query;

import com.zentry.zentrystore.domain.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetUnreadNotificationsCountQueryHandler {

    private final NotificationRepository notificationRepository;

    public GetUnreadNotificationsCountQueryHandler(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Long handle(GetUnreadNotificationsCountQuery query) {
        return notificationRepository.countUnreadByUserId(query.getUserId());
    }
}