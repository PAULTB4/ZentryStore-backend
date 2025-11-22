package com.zentry.zentrystore.application.messaging.query;

import com.zentry.zentrystore.application.messaging.dto.ConversationDTO;
import com.zentry.zentrystore.domain.messaging.exception.ConversationNotFoundException;
import com.zentry.zentrystore.domain.messaging.model.Conversation;
import com.zentry.zentrystore.domain.messaging.repository.ConversationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetConversationBetweenUsersQueryHandler {

    private final ConversationRepository conversationRepository;

    public GetConversationBetweenUsersQueryHandler(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    public ConversationDTO handle(GetConversationBetweenUsersQuery query) {
        Conversation conversation = conversationRepository
                .findByBothUsers(query.getUser1Id(), query.getUser2Id())
                .orElseThrow(() -> ConversationNotFoundException.betweenUsers(
                        query.getUser1Id(), query.getUser2Id()
                ));

        // TODO: Mapear a DTO cuando tengamos mapper
        return null;
    }
}