package com.zentry.zentrystore.api.controller.notification;

import com.zentry.zentrystore.application.notification.command.*;
import com.zentry.zentrystore.application.notification.dto.CreateNotificationRequest;
import com.zentry.zentrystore.application.notification.dto.NotificationDTO;
import com.zentry.zentrystore.application.notification.query.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    private final CreateNotificationCommandHandler createNotificationCommandHandler;
    private final MarkNotificationAsReadCommandHandler markNotificationAsReadCommandHandler;
    private final MarkAllNotificationsAsReadCommandHandler markAllNotificationsAsReadCommandHandler;
    private final DeleteNotificationCommandHandler deleteNotificationCommandHandler;
    private final GetUserNotificationsQueryHandler getUserNotificationsQueryHandler;
    private final GetUnreadNotificationsQueryHandler getUnreadNotificationsQueryHandler;
    private final GetUnreadNotificationsCountQueryHandler getUnreadNotificationsCountQueryHandler;
    private final GetNotificationsByTypeQueryHandler getNotificationsByTypeQueryHandler;

    public NotificationController(
            CreateNotificationCommandHandler createNotificationCommandHandler,
            MarkNotificationAsReadCommandHandler markNotificationAsReadCommandHandler,
            MarkAllNotificationsAsReadCommandHandler markAllNotificationsAsReadCommandHandler,
            DeleteNotificationCommandHandler deleteNotificationCommandHandler,
            GetUserNotificationsQueryHandler getUserNotificationsQueryHandler,
            GetUnreadNotificationsQueryHandler getUnreadNotificationsQueryHandler,
            GetUnreadNotificationsCountQueryHandler getUnreadNotificationsCountQueryHandler,
            GetNotificationsByTypeQueryHandler getNotificationsByTypeQueryHandler) {
        this.createNotificationCommandHandler = createNotificationCommandHandler;
        this.markNotificationAsReadCommandHandler = markNotificationAsReadCommandHandler;
        this.markAllNotificationsAsReadCommandHandler = markAllNotificationsAsReadCommandHandler;
        this.deleteNotificationCommandHandler = deleteNotificationCommandHandler;
        this.getUserNotificationsQueryHandler = getUserNotificationsQueryHandler;
        this.getUnreadNotificationsQueryHandler = getUnreadNotificationsQueryHandler;
        this.getUnreadNotificationsCountQueryHandler = getUnreadNotificationsCountQueryHandler;
        this.getNotificationsByTypeQueryHandler = getNotificationsByTypeQueryHandler;
    }

    // ✅ Solo usuarios autenticados
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<NotificationDTO> createNotification(
            @Valid @RequestBody CreateNotificationRequest request) {

        CreateNotificationCommand command = new CreateNotificationCommand(
                request.getUserId(),
                request.getType(),
                request.getTitle(),
                request.getMessage(),
                request.getRelatedEntityId(),
                request.getRelatedEntityType(),
                request.getActionUrl(),
                request.getPriority()
        );

        NotificationDTO notification = createNotificationCommandHandler.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(notification);
    }

    // ✅ Solo usuarios autenticados (validación ownership en Handler)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationDTO>> getUserNotifications(
            @PathVariable Long userId,
            @RequestParam(required = false) Integer limit) {

        GetUserNotificationsQuery query = new GetUserNotificationsQuery(userId, limit);
        List<NotificationDTO> notifications = getUserNotificationsQueryHandler.handle(query);

        return ResponseEntity.ok(notifications);
    }

    // ✅ Solo usuarios autenticados (validación ownership en Handler)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<List<NotificationDTO>> getUnreadNotifications(@PathVariable Long userId) {

        GetUnreadNotificationsQuery query = new GetUnreadNotificationsQuery(userId);
        List<NotificationDTO> notifications = getUnreadNotificationsQueryHandler.handle(query);

        return ResponseEntity.ok(notifications);
    }

    // ✅ Solo usuarios autenticados (validación ownership en Handler)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/{userId}/unread/count")
    public ResponseEntity<Map<String, Long>> getUnreadNotificationsCount(@PathVariable Long userId) {

        GetUnreadNotificationsCountQuery query = new GetUnreadNotificationsCountQuery(userId);
        Long count = getUnreadNotificationsCountQueryHandler.handle(query);

        return ResponseEntity.ok(Map.of("count", count));
    }

    // ✅ Solo usuarios autenticados (validación ownership en Handler)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/{userId}/type/{type}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByType(
            @PathVariable Long userId,
            @PathVariable String type) {

        GetNotificationsByTypeQuery query = new GetNotificationsByTypeQuery(userId, type);
        List<NotificationDTO> notifications = getNotificationsByTypeQueryHandler.handle(query);

        return ResponseEntity.ok(notifications);
    }

    // ✅ Solo usuarios autenticados (validación ownership en Handler)
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{id}/read")
    public ResponseEntity<Map<String, String>> markNotificationAsRead(
            @PathVariable Long id,
            @RequestParam Long userId) {

        MarkNotificationAsReadCommand command = new MarkNotificationAsReadCommand(id, userId);
        markNotificationAsReadCommandHandler.handle(command);

        return ResponseEntity.ok(Map.of("message", "Notification marked as read"));
    }

    // ✅ Solo usuarios autenticados (validación ownership en Handler)
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/user/{userId}/read-all")
    public ResponseEntity<Map<String, String>> markAllNotificationsAsRead(@PathVariable Long userId) {

        MarkAllNotificationsAsReadCommand command = new MarkAllNotificationsAsReadCommand(userId);
        markAllNotificationsAsReadCommandHandler.handle(command);

        return ResponseEntity.ok(Map.of("message", "All notifications marked as read"));
    }

    // ✅ Solo usuarios autenticados (validación ownership en Handler)
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(
            @PathVariable Long id,
            @RequestParam Long userId) {

        DeleteNotificationCommand command = new DeleteNotificationCommand(id, userId);
        deleteNotificationCommandHandler.handle(command);

        return ResponseEntity.noContent().build();
    }
}