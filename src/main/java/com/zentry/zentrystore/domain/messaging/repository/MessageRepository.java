package com.zentry.zentrystore.domain.messaging.repository;

import com.zentry.zentrystore.domain.messaging.model.Message;
import com.zentry.zentrystore.domain.messaging.model.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    // Búsquedas por conversación
    List<Message> findByConversationId(Long conversationId);

    List<Message> findByConversationIdOrderByCreatedAtAsc(Long conversationId);

    List<Message> findByConversationIdOrderByCreatedAtDesc(Long conversationId);

    @Query("SELECT m FROM Message m WHERE m.conversation.id = :conversationId " +
            "ORDER BY m.createdAt DESC")
    List<Message> findRecentMessagesByConversation(@Param("conversationId") Long conversationId);

    Long countByConversationId(Long conversationId);

    // Búsquedas por remitente
    List<Message> findBySenderId(Long senderId);

    List<Message> findBySenderIdOrderByCreatedAtDesc(Long senderId);

    Long countBySenderId(Long senderId);

    // Búsquedas por estado
    List<Message> findByStatus(MessageStatus status);

    List<Message> findByConversationIdAndStatus(Long conversationId, MessageStatus status);

    @Query("SELECT m FROM Message m WHERE m.conversation.id = :conversationId " +
            "AND m.status = :status ORDER BY m.createdAt ASC")
    List<Message> findByConversationAndStatus(@Param("conversationId") Long conversationId,
                                              @Param("status") MessageStatus status);

    // Mensajes no leídos
    @Query("SELECT m FROM Message m WHERE m.conversation.id = :conversationId " +
            "AND m.sender.id != :userId AND m.status != 'READ'")
    List<Message> findUnreadMessagesByConversationAndUser(@Param("conversationId") Long conversationId,
                                                          @Param("userId") Long userId);

    @Query("SELECT COUNT(m) FROM Message m WHERE m.conversation.id = :conversationId " +
            "AND m.sender.id != :userId AND m.status != 'READ'")
    Long countUnreadMessagesByConversationAndUser(@Param("conversationId") Long conversationId,
                                                  @Param("userId") Long userId);

    @Query("SELECT COUNT(m) FROM Message m JOIN m.conversation c " +
            "WHERE (c.user1.id = :userId OR c.user2.id = :userId) " +
            "AND m.sender.id != :userId AND m.status != 'READ'")
    Long countUnreadMessagesByUser(@Param("userId") Long userId);

    // Mensajes con adjuntos
    @Query("SELECT m FROM Message m WHERE m.conversation.id = :conversationId " +
            "AND m.attachmentUrl IS NOT NULL")
    List<Message> findMessagesWithAttachmentsByConversation(@Param("conversationId") Long conversationId);

    @Query("SELECT COUNT(m) FROM Message m WHERE m.conversation.id = :conversationId " +
            "AND m.attachmentUrl IS NOT NULL")
    Long countMessagesWithAttachmentsByConversation(@Param("conversationId") Long conversationId);

    // Búsquedas por fecha
    List<Message> findByConversationIdAndCreatedAtAfter(Long conversationId, LocalDateTime date);

    List<Message> findByConversationIdAndCreatedAtBetween(Long conversationId,
                                                          LocalDateTime startDate,
                                                          LocalDateTime endDate);

    @Query("SELECT m FROM Message m WHERE m.conversation.id = :conversationId " +
            "AND m.createdAt > :since ORDER BY m.createdAt ASC")
    List<Message> findNewMessagesSince(@Param("conversationId") Long conversationId,
                                       @Param("since") LocalDateTime since);

    // Último mensaje
    @Query("SELECT m FROM Message m WHERE m.conversation.id = :conversationId " +
            "ORDER BY m.createdAt DESC LIMIT 1")
    Message findLastMessageByConversation(@Param("conversationId") Long conversationId);

    // Búsquedas por contenido
    @Query("SELECT m FROM Message m WHERE m.conversation.id = :conversationId " +
            "AND LOWER(m.content) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Message> searchInConversation(@Param("conversationId") Long conversationId,
                                       @Param("search") String search);

    // Mensajes del sistema
    List<Message> findByIsSystemMessageTrue();

    List<Message> findByConversationIdAndIsSystemMessageTrue(Long conversationId);

    // Estadísticas
    @Query("SELECT COUNT(m) FROM Message m WHERE m.sender.id = :userId " +
            "AND m.createdAt >= :startDate")
    Long countMessagesSentByUserSince(@Param("userId") Long userId,
                                      @Param("startDate") LocalDateTime startDate);

    @Query("SELECT m.status, COUNT(m) FROM Message m WHERE m.conversation.id = :conversationId " +
            "GROUP BY m.status")
    List<Object[]> getMessageStatusStatisticsByConversation(@Param("conversationId") Long conversationId);

    // Eliminar mensajes antiguos
    @Query("DELETE FROM Message m WHERE m.createdAt < :cutoffDate")
    void deleteMessagesOlderThan(@Param("cutoffDate") LocalDateTime cutoffDate);

    // Verificaciones
    boolean existsByConversationIdAndSenderId(Long conversationId, Long senderId);
}