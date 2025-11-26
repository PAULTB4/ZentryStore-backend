package com.zentry.zentrystore.application.messaging.command;

import com.zentry.zentrystore.application.messaging.command.DeleteConversationCommand;
import com.zentry.zentrystore.domain.messaging.exception.ConversationNotFoundException;
import com.zentry.zentrystore.domain.messaging.model.Conversation;
import com.zentry.zentrystore.domain.messaging.repository.ConversationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class DeleteConversationCommandHandler {

    private final ConversationRepository conversationRepository;

    public DeleteConversationCommandHandler(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    @Transactional
    public void handle(DeleteConversationCommand command) {
        Conversation conversation = conversationRepository.findById(command.getConversationId())
                .orElseThrow(() -> new ConversationNotFoundException(
                        "Conversation not found: " + command.getConversationId()));

        // Verificar que el usuario es parte de la conversación
        if (!conversation.getUser1Id().equals(command.getUserId()) &&
                !conversation.getUser2Id().equals(command.getUserId())) {
            throw new ConversationNotFoundException("User is not part of this conversation");
        }

        // Soft delete
        conversation.setDeletedAt(LocalDateTime.now());
        conversationRepository.save(conversation);
    }
}