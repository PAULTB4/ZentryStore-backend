package com.zentry.zentrystore.application.messaging.command;

import com.zentry.zentrystore.domain.messaging.exception.ConversationNotFoundException;
import com.zentry.zentrystore.domain.messaging.model.Conversation;
import com.zentry.zentrystore.domain.messaging.repository.ConversationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArchiveConversationCommandHandler {

    private final ConversationRepository conversationRepository;

    public ArchiveConversationCommandHandler(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    @Transactional
    public void handle(ArchiveConversationCommand command) {
        Conversation conversation = conversationRepository.findById(command.getConversationId())
                .orElseThrow(() -> ConversationNotFoundException.byId(command.getConversationId()));

        // Archivar conversación para el usuario
        conversation.archiveByUser(command.getUserId());
        conversationRepository.save(conversation);
    }
}