package com.zentry.zentrystore.application.messaging.query;

import com.zentry.zentrystore.application.messaging.dto.MessageDTO;
import com.zentry.zentrystore.application.messaging.mapper.MessagingMapper;
import com.zentry.zentrystore.application.messaging.query.GetConversationMessagesQuery;
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
    private final MessagingMapper messagingMapper;

    public GetConversationMessagesQueryHandler(
            MessageRepository messageRepository,
            ConversationRepository conversationRepository,
            MessagingMapper messagingMapper) {
        this.messageRepository = messageRepository;
        this.conversationRepository = conversationRepository;
        this.messagingMapper = messagingMapper;
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

        return messagingMapper.toMessageDTOList(messages);
    }
}