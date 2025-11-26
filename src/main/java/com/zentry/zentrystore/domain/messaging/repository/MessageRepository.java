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
    @Query("SELECT m FROM Message m WHERE m.conversation.id = :conversationId")
    List<Message> findByConversationId(@Param("conversationId") Long conversationId);

    @Query("SELECT m FROM Message m WHERE m.conversation.id = :conversationId ORDER BY m.createdAt ASC")
    List<Message> findByConversationIdOrderByCreatedAtAsc(@Param("conversationId") Long conversationId);

    @Query("SELECT m FROM Message m WHERE m.conversation.id = :conversationId ORDER BY m.createdAt DESC")
    List<Message> findByConversationIdOrderByCreatedAtDesc(@Param("conversationId") Long conversationId);

    @Query("SELECT m FROM Message m WHERE m.conversation.id = :conversationId " +
            "ORDER BY m.createdAt DESC")
    List<Message> findRecentMessagesByConversation(@Param("conversationId") Long conversationId);

    @Query("SELECT COUNT(m) FROM Message m WHERE m.conversation.id = :conversationId")
    Long countByConversationId(@Param("conversationId") Long conversationId);

    // Búsquedas por remitente
    @Query("SELECT m FROM Message m WHERE m.sender.id = :senderId")
    List<Message> findBySenderId(@Param("senderId") Long senderId);

    @Query("SELECT m FROM Message m WHERE m.sender.id = :senderId ORDER BY m.createdAt DESC")
    List<Message> findBySenderIdOrderByCreatedAtDesc(@Param("senderId") Long senderId);

    @Query("SELECT COUNT(m) FROM Message m WHERE m.sender.id = :senderId")
    Long countBySenderId(@Param("senderId") Long senderId);

    // Búsquedas por estado
    List<Message> findByStatus(MessageStatus status);

    @Query("SELECT m FROM Message m WHERE m.conversation.id = :conversationId AND m.status = :status")
    List<Message> findByConversationIdAndStatus(@Param("conversationId") Long conversationId,
                                                @Param("status") MessageStatus status);

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
    @Query("SELECT m FROM Message m WHERE m.conversation.id = :conversationId AND m.createdAt > :date")
    List<Message> findByConversationIdAndCreatedAtAfter(@Param("conversationId") Long conversationId,
                                                        @Param("date") LocalDateTime date);

    @Query("SELECT m FROM Message m WHERE m.conversation.id = :conversationId " +
            "AND m.createdAt BETWEEN :startDate AND :endDate")
    List<Message> findByConversationIdAndCreatedAtBetween(@Param("conversationId") Long conversationId,
                                                          @Param("startDate") LocalDateTime startDate,
                                                          @Param("endDate") LocalDateTime endDate);

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

    @Query("SELECT m FROM Message m WHERE m.conversation.id = :conversationId AND m.isSystemMessage = true")
    List<Message> findByConversationIdAndIsSystemMessageTrue(@Param("conversationId") Long conversationId);

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
    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Message m " +
            "WHERE m.conversation.id = :conversationId AND m.sender.id = :senderId")
    boolean existsByConversationIdAndSenderId(@Param("conversationId") Long conversationId,
                                              @Param("senderId") Long senderId);
}