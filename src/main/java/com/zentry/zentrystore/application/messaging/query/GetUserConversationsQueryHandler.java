package com.zentry.zentrystore.application.messaging.query;

import com.zentry.zentrystore.application.messaging.dto.ConversationDTO;
import com.zentry.zentrystore.domain.messaging.model.Conversation;
import com.zentry.zentrystore.domain.messaging.repository.ConversationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetUserConversationsQueryHandler {

    private final ConversationRepository conversationRepository;

    public GetUserConversationsQueryHandler(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    public List<ConversationDTO> handle(GetUserConversationsQuery query) {
        List<Conversation> conversations = conversationRepository
                .findNonArchivedByUserId(query.getUserId());

        // TODO: Mapear a DTOs cuando tengamos mapper
        return null;
    }
}