package com.zentry.zentrystore.application.messaging.command;

import com.zentry.zentrystore.domain.messaging.exception.ConversationNotFoundException;
import com.zentry.zentrystore.domain.messaging.model.Conversation;
import com.zentry.zentrystore.domain.messaging.repository.ConversationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BlockConversationCommandHandler {

    private final ConversationRepository conversationRepository;

    public BlockConversationCommandHandler(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    @Transactional
    public void handle(BlockConversationCommand command) {
        Conversation conversation = conversationRepository.findById(command.getConversationId())
                .orElseThrow(() -> ConversationNotFoundException.byId(command.getConversationId()));

        // Bloquear conversación para el usuario
        conversation.blockByUser(command.getUserId());
        conversationRepository.save(conversation);

        // TODO: Notificar al otro usuario (opcional)
    }
}