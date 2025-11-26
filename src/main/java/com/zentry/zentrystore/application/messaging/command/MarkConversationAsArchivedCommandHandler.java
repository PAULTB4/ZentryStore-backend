package com.zentry.zentrystore.application.messaging.command;

import com.zentry.zentrystore.application.messaging.command.MarkConversationAsArchivedCommand;
import com.zentry.zentrystore.domain.messaging.exception.ConversationNotFoundException;
import com.zentry.zentrystore.domain.messaging.model.Conversation;
import com.zentry.zentrystore.domain.messaging.repository.ConversationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class MarkConversationAsArchivedCommandHandler {

    private final ConversationRepository conversationRepository;

    public MarkConversationAsArchivedCommandHandler(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    @Transactional
    public void handle(MarkConversationAsArchivedCommand command) {
        Conversation conversation = conversationRepository.findById(command.getConversationId())
                .orElseThrow(() -> new ConversationNotFoundException(
                        "Conversation not found: " + command.getConversationId()));

        // Verificar que el usuario es parte de la conversación
        if (!conversation.getUser1Id().equals(command.getUserId()) &&
                !conversation.getUser2Id().equals(command.getUserId())) {
            throw new ConversationNotFoundException("User is not part of this conversation");
        }

        // Actualizar el estado de archivado según el usuario
        if (conversation.getUser1Id().equals(command.getUserId())) {
            conversation.setIsArchivedByUser1(command.isArchived());
        } else {
            conversation.setIsArchivedByUser2(command.isArchived());
        }

        conversation.setUpdatedAt(LocalDateTime.now());
        conversationRepository.save(conversation);
    }
}