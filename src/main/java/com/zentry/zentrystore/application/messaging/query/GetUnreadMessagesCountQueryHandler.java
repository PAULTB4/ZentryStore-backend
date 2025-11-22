package com.zentry.zentrystore.application.messaging.query;


import com.zentry.zentrystore.domain.messaging.repository.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetUnreadMessagesCountQueryHandler {

    private final MessageRepository messageRepository;

    public GetUnreadMessagesCountQueryHandler(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Long handle(GetUnreadMessagesCountQuery query) {
        return messageRepository.countUnreadMessagesByUser(query.getUserId());
    }
}