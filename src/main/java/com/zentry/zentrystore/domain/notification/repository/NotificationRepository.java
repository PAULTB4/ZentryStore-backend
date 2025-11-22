package com.zentry.zentrystore.domain.notification.repository;

import com.zentry.zentrystore.domain.notification.model.Notification;
import com.zentry.zentrystore.domain.notification.model.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Búsquedas por usuario
    List<Notification> findByUserId(Long userId);

    List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);

    @Query("SELECT n FROM Notification n WHERE n.user.id = :userId " +
            "ORDER BY n.createdAt DESC LIMIT :limit")
    List<Notification> findRecentByUserId(@Param("userId") Long userId, @Param("limit") int limit);

    Long countByUserId(Long userId);

    // Notificaciones no leídas
    List<Notification> findByUserIdAndIsReadFalse(Long userId);

    List<Notification> findByUserIdAndIsReadFalseOrderByCreatedAtDesc(Long userId);

    @Query("SELECT COUNT(n) FROM Notification n WHERE n.user.id = :userId AND n.isRead = false")
    Long countUnreadByUserId(@Param("userId") Long userId);

    // Notificaciones leídas
    List<Notification> findByUserIdAndIsReadTrue(Long userId);

    List<Notification> findByUserIdAndIsReadTrueOrderByCreatedAtDesc(Long userId);

    // Búsquedas por tipo
    List<Notification> findByUserIdAndType(Long userId, NotificationType type);

    List<Notification> findByUserIdAndTypeOrderByCreatedAtDesc(Long userId, NotificationType type);

    List<Notification> findByType(NotificationType type);

    @Query("SELECT n FROM Notification n WHERE n.user.id = :userId AND n.type = :type " +
            "AND n.isRead = false")
    List<Notification> findUnreadByUserIdAndType(@Param("userId") Long userId,
                                                 @Param("type") NotificationType type);

    Long countByUserIdAndType(Long userId, NotificationType type);

    // Búsquedas por entidad relacionada
    List<Notification> findByRelatedEntityIdAndRelatedEntityType(Long entityId, String entityType);

    @Query("SELECT n FROM Notification n WHERE n.user.id = :userId " +
            "AND n.relatedEntityId = :entityId AND n.relatedEntityType = :entityType")
    List<Notification> findByUserAndRelatedEntity(@Param("userId") Long userId,
                                                  @Param("entityId") Long entityId,
                                                  @Param("entityType") String entityType);

    // Búsquedas por prioridad
    List<Notification> findByUserIdAndPriority(Long userId, String priority);

    @Query("SELECT n FROM Notification n WHERE n.user.id = :userId " +
            "AND n.priority IN ('HIGH', 'URGENT') AND n.isRead = false " +
            "ORDER BY n.createdAt DESC")
    List<Notification> findHighPriorityUnreadByUserId(@Param("userId") Long userId);

    @Query("SELECT n FROM Notification n WHERE n.user.id = :userId " +
            "AND n.priority = 'URGENT' AND n.isRead = false")
    List<Notification> findUrgentUnreadByUserId(@Param("userId") Long userId);

    // Búsquedas por fecha
    List<Notification> findByUserIdAndCreatedAtAfter(Long userId, LocalDateTime date);

    List<Notification> findByUserIdAndCreatedAtBetween(Long userId,
                                                       LocalDateTime startDate,
                                                       LocalDateTime endDate);

    @Query("SELECT n FROM Notification n WHERE n.user.id = :userId " +
            "AND n.createdAt >= :since ORDER BY n.createdAt DESC")
    List<Notification> findNewNotificationsSince(@Param("userId") Long userId,
                                                 @Param("since") LocalDateTime since);

    // Notificaciones del día
    @Query("SELECT n FROM Notification n WHERE n.user.id = :userId " +
            "AND CAST(n.createdAt AS LocalDate) = CURRENT_DATE ORDER BY n.createdAt DESC")
    List<Notification> findTodayNotifications(@Param("userId") Long userId);

    // Estadísticas
    @Query("SELECT n.type, COUNT(n) FROM Notification n WHERE n.user.id = :userId " +
            "GROUP BY n.type")
    List<Object[]> getNotificationTypeStatistics(@Param("userId") Long userId);

    @Query("SELECT COUNT(n) FROM Notification n WHERE n.user.id = :userId " +
            "AND n.isRead = false AND n.createdAt >= :since")
    Long countUnreadSince(@Param("userId") Long userId, @Param("since") LocalDateTime since);

    @Query("SELECT n.priority, COUNT(n) FROM Notification n WHERE n.user.id = :userId " +
            "AND n.isRead = false GROUP BY n.priority")
    List<Object[]> getUnreadNotificationsByPriority(@Param("userId") Long userId);

    // Marcar como leídas en lote
    @Query("UPDATE Notification n SET n.isRead = true, n.readAt = :readAt " +
            "WHERE n.user.id = :userId AND n.isRead = false")
    void markAllAsReadByUserId(@Param("userId") Long userId, @Param("readAt") LocalDateTime readAt);

    @Query("UPDATE Notification n SET n.isRead = true, n.readAt = :readAt " +
            "WHERE n.user.id = :userId AND n.type = :type AND n.isRead = false")
    void markAllAsReadByUserIdAndType(@Param("userId") Long userId,
                                      @Param("type") NotificationType type,
                                      @Param("readAt") LocalDateTime readAt);

    // Limpieza de notificaciones antiguas
    @Query("DELETE FROM Notification n WHERE n.isRead = true AND n.createdAt < :cutoffDate")
    void deleteReadNotificationsOlderThan(@Param("cutoffDate") LocalDateTime cutoffDate);

    @Query("DELETE FROM Notification n WHERE n.user.id = :userId")
    void deleteAllByUserId(@Param("userId") Long userId);

    // Verificaciones
    boolean existsByUserIdAndRelatedEntityIdAndRelatedEntityType(Long userId,
                                                                 Long entityId,
                                                                 String entityType);

    @Query("SELECT CASE WHEN COUNT(n) > 0 THEN true ELSE false END FROM Notification n " +
            "WHERE n.user.id = :userId AND n.isRead = false")
    boolean hasUnreadNotifications(@Param("userId") Long userId);
}