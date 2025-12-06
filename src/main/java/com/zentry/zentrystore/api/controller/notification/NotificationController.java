package com.zentry.zentrystore.api.controller.notification;

import com.zentry.zentrystore.application.notification.command.*;
import com.zentry.zentrystore.application.notification.dto.CreateNotificationRequest;
import com.zentry.zentrystore.application.notification.dto.NotificationDTO;
import com.zentry.zentrystore.application.notification.query.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    /**
     * POST /api/notifications
     * Crear una nueva notificación
     */
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

    /**
     * GET /api/notifications/user/{userId}
     * Obtener notificaciones de un usuario
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationDTO>> getUserNotifications(
            @PathVariable Long userId,
            @RequestParam(required = false) Integer limit) {

        GetUserNotificationsQuery query = new GetUserNotificationsQuery(userId, limit);
        List<NotificationDTO> notifications = getUserNotificationsQueryHandler.handle(query);

        return ResponseEntity.ok(notifications);
    }

    /**
     * GET /api/notifications/user/{userId}/unread
     * Obtener notificaciones no leídas de un usuario
     */
    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<List<NotificationDTO>> getUnreadNotifications(@PathVariable Long userId) {

        GetUnreadNotificationsQuery query = new GetUnreadNotificationsQuery(userId);
        List<NotificationDTO> notifications = getUnreadNotificationsQueryHandler.handle(query);

        return ResponseEntity.ok(notifications);
    }

    /**
     * GET /api/notifications/user/{userId}/unread/count
     * Contar notificaciones no leídas
     */
    @GetMapping("/user/{userId}/unread/count")
    public ResponseEntity<Map<String, Long>> getUnreadNotificationsCount(@PathVariable Long userId) {

        GetUnreadNotificationsCountQuery query = new GetUnreadNotificationsCountQuery(userId);
        Long count = getUnreadNotificationsCountQueryHandler.handle(query);

        return ResponseEntity.ok(Map.of("count", count));
    }

    /**
     * GET /api/notifications/user/{userId}/type/{type}
     * Obtener notificaciones por tipo
     */
    @GetMapping("/user/{userId}/type/{type}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByType(
            @PathVariable Long userId,
            @PathVariable String type) {

        GetNotificationsByTypeQuery query = new GetNotificationsByTypeQuery(userId, type);
        List<NotificationDTO> notifications = getNotificationsByTypeQueryHandler.handle(query);

        return ResponseEntity.ok(notifications);
    }

    /**
     * PATCH /api/notifications/{id}/read
     * Marcar notificación como leída
     */
    @PatchMapping("/{id}/read")
    public ResponseEntity<Map<String, String>> markNotificationAsRead(
            @PathVariable Long id,
            @RequestParam Long userId) {

        MarkNotificationAsReadCommand command = new MarkNotificationAsReadCommand(id, userId);
        markNotificationAsReadCommandHandler.handle(command);

        return ResponseEntity.ok(Map.of("message", "Notification marked as read"));
    }

    /**
     * PATCH /api/notifications/user/{userId}/read-all
     * Marcar todas las notificaciones como leídas
     */
    @PatchMapping("/user/{userId}/read-all")
    public ResponseEntity<Map<String, String>> markAllNotificationsAsRead(@PathVariable Long userId) {

        MarkAllNotificationsAsReadCommand command = new MarkAllNotificationsAsReadCommand(userId);
        markAllNotificationsAsReadCommandHandler.handle(command);

        return ResponseEntity.ok(Map.of("message", "All notifications marked as read"));
    }

    /**
     * DELETE /api/notifications/{id}
     * Eliminar una notificación
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(
            @PathVariable Long id,
            @RequestParam Long userId) {

        DeleteNotificationCommand command = new DeleteNotificationCommand(id, userId);
        deleteNotificationCommandHandler.handle(command);

        return ResponseEntity.noContent().build();
    }
}