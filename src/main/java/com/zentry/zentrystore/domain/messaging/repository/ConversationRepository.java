package com.zentry.zentrystore.domain.messaging.repository;

import com.zentry.zentrystore.domain.messaging.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    // Búsqueda de conversación entre dos usuarios
    @Query("SELECT DISTINCT c FROM Conversation c " +
            "LEFT JOIN FETCH c.user1 " +
            "LEFT JOIN FETCH c.user2 " +
            "WHERE " +
            "(c.user1.id = :user1Id AND c.user2.id = :user2Id) OR " +
            "(c.user1.id = :user2Id AND c.user2.id = :user1Id)")
    Optional<Conversation> findByBothUsers(@Param("user1Id") Long user1Id,
                                           @Param("user2Id") Long user2Id);

    // Alias para compatibilidad con el handler
    default Optional<Conversation> findByUser1IdAndUser2Id(Long user1Id, Long user2Id) {
        return findByBothUsers(user1Id, user2Id);
    }

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Conversation c WHERE " +
            "(c.user1.id = :user1Id AND c.user2.id = :user2Id) OR " +
            "(c.user1.id = :user2Id AND c.user2.id = :user1Id)")
    boolean existsBetweenUsers(@Param("user1Id") Long user1Id, @Param("user2Id") Long user2Id);

    // Conversaciones de un usuario
    @Query("SELECT DISTINCT c FROM Conversation c " +
            "LEFT JOIN FETCH c.user1 " +
            "LEFT JOIN FETCH c.user2 " +
            "WHERE c.user1.id = :userId OR c.user2.id = :userId " +
            "ORDER BY c.lastMessageAt DESC NULLS LAST, c.updatedAt DESC")
    List<Conversation> findByUserId(@Param("userId") Long userId);

    @Query("SELECT DISTINCT c FROM Conversation c " +
            "LEFT JOIN FETCH c.user1 " +
            "LEFT JOIN FETCH c.user2 " +
            "WHERE (c.user1.id = :userId OR c.user2.id = :userId) " +
            "AND c.lastMessageAt IS NOT NULL " +
            "ORDER BY c.lastMessageAt DESC")
    List<Conversation> findActiveConversationsByUserId(@Param("userId") Long userId);

    // Conversaciones archivadas
    @Query("SELECT DISTINCT c FROM Conversation c " +
            "LEFT JOIN FETCH c.user1 " +
            "LEFT JOIN FETCH c.user2 " +
            "WHERE " +
            "(c.user1.id = :userId AND c.isArchivedByUser1 = true) OR " +
            "(c.user2.id = :userId AND c.isArchivedByUser2 = true)")
    List<Conversation> findArchivedByUserId(@Param("userId") Long userId);

    @Query("SELECT DISTINCT c FROM Conversation c " +
            "LEFT JOIN FETCH c.user1 " +
            "LEFT JOIN FETCH c.user2 " +
            "WHERE " +
            "(c.user1.id = :userId AND c.isArchivedByUser1 = false) OR " +
            "(c.user2.id = :userId AND c.isArchivedByUser2 = false) " +
            "ORDER BY c.lastMessageAt DESC NULLS LAST")
    List<Conversation> findNonArchivedByUserId(@Param("userId") Long userId);

    // Conversaciones bloqueadas
    @Query("SELECT DISTINCT c FROM Conversation c " +
            "LEFT JOIN FETCH c.user1 " +
            "LEFT JOIN FETCH c.user2 " +
            "WHERE " +
            "(c.user1.id = :userId AND c.isBlockedByUser1 = true) OR " +
            "(c.user2.id = :userId AND c.isBlockedByUser2 = true)")
    List<Conversation> findBlockedByUserId(@Param("userId") Long userId);

    @Query("SELECT DISTINCT c FROM Conversation c " +
            "LEFT JOIN FETCH c.user1 " +
            "LEFT JOIN FETCH c.user2 " +
            "WHERE " +
            "(c.user1.id = :userId AND c.isBlockedByUser1 = false AND c.isBlockedByUser2 = false) OR " +
            "(c.user2.id = :userId AND c.isBlockedByUser1 = false AND c.isBlockedByUser2 = false) " +
            "ORDER BY c.lastMessageAt DESC NULLS LAST")
    List<Conversation> findNonBlockedByUserId(@Param("userId") Long userId);

    // Conversaciones con mensajes no leídos
    @Query("SELECT DISTINCT c FROM Conversation c " +
            "LEFT JOIN FETCH c.user1 " +
            "LEFT JOIN FETCH c.user2 " +
            "WHERE " +
            "(c.user1.id = :userId AND c.unreadCountUser1 > 0) OR " +
            "(c.user2.id = :userId AND c.unreadCountUser2 > 0)")
    List<Conversation> findWithUnreadMessagesByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(c) FROM Conversation c WHERE " +
            "(c.user1.id = :userId AND c.unreadCountUser1 > 0) OR " +
            "(c.user2.id = :userId AND c.unreadCountUser2 > 0)")
    Long countWithUnreadMessagesByUserId(@Param("userId") Long userId);

    @Query("SELECT SUM(CASE WHEN c.user1.id = :userId THEN c.unreadCountUser1 " +
            "ELSE c.unreadCountUser2 END) FROM Conversation c WHERE " +
            "c.user1.id = :userId OR c.user2.id = :userId")
    Long getTotalUnreadCountByUserId(@Param("userId") Long userId);

    // Búsquedas por fecha
    @Query("SELECT DISTINCT c FROM Conversation c " +
            "LEFT JOIN FETCH c.user1 " +
            "LEFT JOIN FETCH c.user2 " +
            "WHERE (c.user1.id = :userId OR c.user2.id = :userId) " +
            "AND c.lastMessageAt > :since " +
            "ORDER BY c.lastMessageAt DESC")
    List<Conversation> findUpdatedSince(@Param("userId") Long userId,
                                        @Param("since") LocalDateTime since);

    List<Conversation> findByCreatedAtAfter(LocalDateTime date);

    List<Conversation> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Estadísticas
    @Query("SELECT COUNT(c) FROM Conversation c WHERE c.user1.id = :userId OR c.user2.id = :userId")
    Long countByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(c) FROM Conversation c WHERE " +
            "(c.user1.id = :userId OR c.user2.id = :userId) AND c.lastMessageAt IS NOT NULL")
    Long countActiveConversationsByUserId(@Param("userId") Long userId);

    // Conversaciones recientes
    @Query("SELECT DISTINCT c FROM Conversation c " +
            "LEFT JOIN FETCH c.user1 " +
            "LEFT JOIN FETCH c.user2 " +
            "WHERE (c.user1.id = :userId OR c.user2.id = :userId) " +
            "AND c.lastMessageAt IS NOT NULL " +
            "ORDER BY c.lastMessageAt DESC LIMIT :limit")
    List<Conversation> findRecentConversations(@Param("userId") Long userId,
                                               @Param("limit") int limit);

    // Limpieza
    @Query("DELETE FROM Conversation c WHERE c.lastMessageAt IS NULL " +
            "AND c.createdAt < :cutoffDate")
    void deleteEmptyConversationsOlderThan(@Param("cutoffDate") LocalDateTime cutoffDate);
}