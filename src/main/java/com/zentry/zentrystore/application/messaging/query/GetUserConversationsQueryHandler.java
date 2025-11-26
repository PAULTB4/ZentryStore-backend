package com.zentry.zentrystore.application.messaging.query;

import com.zentry.zentrystore.application.messaging.mapper.MessagingMapper;
import com.zentry.zentrystore.application.messaging.query.GetUserConversationsQuery;
import com.zentry.zentrystore.domain.messaging.model.Conversation;
import com.zentry.zentrystore.domain.messaging.repository.ConversationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

    public Page<Conversation> handle(GetUserConversationsQuery query) {
        List<Conversation> conversations = conversationRepository
                .findNonArchivedByUserId(query.getUserId());

        // Convertir List a Page
        int start = (int) query.getPageable().getOffset();
        int end = Math.min((start + query.getPageable().getPageSize()), conversations.size());

        List<Conversation> pageContent = conversations.subList(start, end);

        return new PageImpl<>(pageContent, query.getPageable(), conversations.size());
    }
}