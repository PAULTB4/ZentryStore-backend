package com.zentry.zentrystore.application.messaging.query;

import com.zentry.zentrystore.application.messaging.dto.MessageDTO;
import com.zentry.zentrystore.domain.messaging.exception.ConversationNotFoundException;
import com.zentry.zentrystore.domain.messaging.model.Conversation;
import com.zentry.zentrystore.domain.messaging.model.Message;
import com.zentry.zentrystore.domain.messaging.repository.ConversationRepository;
import com.zentry.zentrystore.domain.messaging.repository.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetConversationMessagesQueryHandler {

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;

    public GetConversationMessagesQueryHandler(MessageRepository messageRepository,
                                               ConversationRepository conversationRepository) {
        this.messageRepository = messageRepository;
        this.conversationRepository = conversationRepository;
    }

    public List<MessageDTO> handle(GetConversationMessagesQuery query) {
        // Verificar que la conversación existe y el usuario es parte de ella
        Conversation conversation = conversationRepository.findById(query.getConversationId())
                .orElseThrow(() -> ConversationNotFoundException.byId(query.getConversationId()));

        if (!conversation.getUser1().getId().equals(query.getUserId()) &&
                !conversation.getUser2().getId().equals(query.getUserId())) {
            throw new IllegalArgumentException("User is not part of this conversation");
        }

        // Obtener mensajes
        List<Message> messages = messageRepository
                .findByConversationIdOrderByCreatedAtAsc(query.getConversationId());

        // TODO: Mapear a DTOs cuando tengamos mapper
        return null;
    }
}