package com.zentry.zentrystore.domain.messaging.exception;

public class ConversationNotFoundException extends RuntimeException {

    public ConversationNotFoundException(String message) {
        super(message);
    }

    public ConversationNotFoundException(Long conversationId) {
        super("Conversation not found with id: " + conversationId);
    }

    public static ConversationNotFoundException byId(Long id) {
        return new ConversationNotFoundException(id);
    }

    public static ConversationNotFoundException betweenUsers(Long user1Id, Long user2Id) {
        return new ConversationNotFoundException(
                String.format("Conversation not found between users %d and %d", user1Id, user2Id)
        );
    }

    public static ConversationNotFoundException forUser(Long userId) {
        return new ConversationNotFoundException(
                String.format("No conversations found for user %d", userId)
        );
    }
}