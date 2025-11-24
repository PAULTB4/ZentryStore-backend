package com.zentry.zentrystore.application.messaging.query;

import com.zentry.zentrystore.application.messaging.dto.ConversationDTO;
import com.zentry.zentrystore.domain.messaging.exception.ConversationNotFoundException;
import com.zentry.zentrystore.domain.messaging.model.Conversation;
import com.zentry.zentrystore.domain.messaging.repository.ConversationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetConversationByIdQueryHandler {

    private final ConversationRepository conversationRepository;

    public GetConversationByIdQueryHandler(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    public ConversationDTO handle(GetConversationByIdQuery query) {
        Conversation conversation = conversationRepository.findById(query.getConversationId())
                .orElseThrow(() -> ConversationNotFoundException.byId(query.getConversationId()));

        // Verificar que el usuario sea parte de la conversación
        if (!conversation.getUser1().getId().equals(query.getUserId()) &&
                !conversation.getUser2().getId().equals(query.getUserId())) {
            throw new IllegalArgumentException("User is not part of this conversation");
        }

        // TODO: Mapear a DTO cuando tengamos mapper
        return null;
    }
}