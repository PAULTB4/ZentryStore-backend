package com.zentry.zentrystore.domain.messaging.model;

import com.zentry.zentrystore.domain.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "conversations", indexes = {
        @Index(name = "idx_conversation_user1", columnList = "user1_id"),
        @Index(name = "idx_conversation_user2", columnList = "user2_id"),
        @Index(name = "idx_conversation_updated", columnList = "updated_at")
})
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User 1 is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user1_id", nullable = false)
    private User user1;

    @NotNull(message = "User 2 is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user2_id", nullable = false)
    private User user2;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages = new ArrayList<>();

    @Column(name = "last_message_at")
    private LocalDateTime lastMessageAt;

    @Column(name = "is_archived_by_user1")
    private Boolean isArchivedByUser1 = false;

    @Column(name = "is_archived_by_user2")
    private Boolean isArchivedByUser2 = false;

    @Column(name = "is_blocked_by_user1")
    private Boolean isBlockedByUser1 = false;

    @Column(name = "is_blocked_by_user2")
    private Boolean isBlockedByUser2 = false;

    @Column(name = "unread_count_user1")
    private Integer unreadCountUser1 = 0;

    @Column(name = "unread_count_user2")
    private Integer unreadCountUser2 = 0;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "publication_id")
    private Long publicationId;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // Constructors
    public Conversation() {
    }

    public Conversation(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

    // Business methods
    public void addMessage(Message message) {
        this.messages.add(message);
        message.setConversation(this);
        this.lastMessageAt = LocalDateTime.now();

        // Incrementar contador de no leídos para el receptor
        if (message.getSender().getId().equals(user1.getId())) {
            this.unreadCountUser2++;
        } else {
            this.unreadCountUser1++;
        }
    }

    public void markAsReadByUser(Long userId) {
        if (userId.equals(user1.getId())) {
            this.unreadCountUser1 = 0;
        } else if (userId.equals(user2.getId())) {
            this.unreadCountUser2 = 0;
        }
    }

    public void archiveByUser(Long userId) {
        if (userId.equals(user1.getId())) {
            this.isArchivedByUser1 = true;
        } else if (userId.equals(user2.getId())) {
            this.isArchivedByUser2 = true;
        }
    }

    public void unarchiveByUser(Long userId) {
        if (userId.equals(user1.getId())) {
            this.isArchivedByUser1 = false;
        } else if (userId.equals(user2.getId())) {
            this.isArchivedByUser2 = false;
        }
    }

    public void blockByUser(Long userId) {
        if (userId.equals(user1.getId())) {
            this.isBlockedByUser1 = true;
        } else if (userId.equals(user2.getId())) {
            this.isBlockedByUser2 = true;
        }
    }

    public void unblockByUser(Long userId) {
        if (userId.equals(user1.getId())) {
            this.isBlockedByUser1 = false;
        } else if (userId.equals(user2.getId())) {
            this.isBlockedByUser2 = false;
        }
    }

    public boolean isBlockedByEitherUser() {
        return Boolean.TRUE.equals(isBlockedByUser1) || Boolean.TRUE.equals(isBlockedByUser2);
    }

    public boolean isArchivedByUser(Long userId) {
        if (userId.equals(user1.getId())) {
            return Boolean.TRUE.equals(isArchivedByUser1);
        } else if (userId.equals(user2.getId())) {
            return Boolean.TRUE.equals(isArchivedByUser2);
        }
        return false;
    }

    public boolean isBlockedByUser(Long userId) {
        if (userId.equals(user1.getId())) {
            return Boolean.TRUE.equals(isBlockedByUser1);
        } else if (userId.equals(user2.getId())) {
            return Boolean.TRUE.equals(isBlockedByUser2);
        }
        return false;
    }

    public User getOtherUser(Long userId) {
        if (userId.equals(user1.getId())) {
            return user2;
        } else if (userId.equals(user2.getId())) {
            return user1;
        }
        return null;
    }

    public Integer getUnreadCountForUser(Long userId) {
        if (userId.equals(user1.getId())) {
            return unreadCountUser1;
        } else if (userId.equals(user2.getId())) {
            return unreadCountUser2;
        }
        return 0;
    }

    public boolean involvesBothUsers(Long userId1, Long userId2) {
        return (this.user1.getId().equals(userId1) && this.user2.getId().equals(userId2)) ||
                (this.user1.getId().equals(userId2) && this.user2.getId().equals(userId1));
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public LocalDateTime getLastMessageAt() {
        return lastMessageAt;
    }

    public void setLastMessageAt(LocalDateTime lastMessageAt) {
        this.lastMessageAt = lastMessageAt;
    }

    public Boolean getIsArchivedByUser1() {
        return isArchivedByUser1;
    }

    public void setIsArchivedByUser1(Boolean isArchivedByUser1) {
        this.isArchivedByUser1 = isArchivedByUser1;
    }

    public Boolean getIsArchivedByUser2() {
        return isArchivedByUser2;
    }

    public void setIsArchivedByUser2(Boolean isArchivedByUser2) {
        this.isArchivedByUser2 = isArchivedByUser2;
    }

    public Boolean getIsBlockedByUser1() {
        return isBlockedByUser1;
    }

    public void setIsBlockedByUser1(Boolean isBlockedByUser1) {
        this.isBlockedByUser1 = isBlockedByUser1;
    }

    public Boolean getIsBlockedByUser2() {
        return isBlockedByUser2;
    }

    public void setIsBlockedByUser2(Boolean isBlockedByUser2) {
        this.isBlockedByUser2 = isBlockedByUser2;
    }

    public Integer getUnreadCountUser1() {
        return unreadCountUser1;
    }

    public void setUnreadCountUser1(Integer unreadCountUser1) {
        this.unreadCountUser1 = unreadCountUser1;
    }

    public Integer getUnreadCountUser2() {
        return unreadCountUser2;
    }

    public void setUnreadCountUser2(Integer unreadCountUser2) {
        this.unreadCountUser2 = unreadCountUser2;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Métodos helper para obtener IDs desde las relaciones
    public Long getUser1Id() {
        return this.user1 != null ? this.user1.getId() : null;
    }

    public void setUser1Id(Long user1Id) {
        // Este setter no se usa directamente
        // La asignación se hace mediante setUser1(User)
    }

    public Long getUser2Id() {
        return this.user2 != null ? this.user2.getId() : null;
    }

    public void setUser2Id(Long user2Id) {
        // Este setter no se usa directamente
        // La asignación se hace mediante setUser2(User)
    }

    public Long getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(Long publicationId) {
        this.publicationId = publicationId;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}