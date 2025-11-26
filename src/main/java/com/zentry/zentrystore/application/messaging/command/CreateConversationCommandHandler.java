package com.zentry.zentrystore.application.messaging.command;

import com.zentry.zentrystore.domain.messaging.event.MessageSentEvent;
import com.zentry.zentrystore.domain.messaging.model.Conversation;
import com.zentry.zentrystore.domain.messaging.model.Message;
import com.zentry.zentrystore.domain.messaging.model.MessageStatus;
import com.zentry.zentrystore.domain.messaging.model.MessageType;
import com.zentry.zentrystore.domain.messaging.repository.ConversationRepository;
import com.zentry.zentrystore.domain.messaging.repository.MessageRepository;
import com.zentry.zentrystore.domain.user.exception.UserNotFoundException;
import com.zentry.zentrystore.domain.user.model.User;
import com.zentry.zentrystore.domain.user.repository.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CreateConversationCommandHandler {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    public CreateConversationCommandHandler(
            ConversationRepository conversationRepository,
            MessageRepository messageRepository,
            UserRepository userRepository,
            ApplicationEventPublisher eventPublisher) {
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public Conversation handle(CreateConversationCommand command) {
        // Cargar las entidades User completas
        User user1 = userRepository.findById(command.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found: " + command.getUserId()));

        User user2 = userRepository.findById(command.getRecipientId())
                .orElseThrow(() -> new UserNotFoundException("Recipient not found: " + command.getRecipientId()));

        // Verificar si ya existe una conversación entre estos usuarios
        Conversation existingConversation = conversationRepository
                .findByUser1IdAndUser2Id(command.getUserId(), command.getRecipientId())
                .orElse(null);

        if (existingConversation != null) {
            // Si ya existe, solo enviamos el mensaje inicial en esa conversación
            Message message = createMessage(existingConversation, command, user1);
            existingConversation.setLastMessageAt(LocalDateTime.now());
            conversationRepository.save(existingConversation);

            eventPublisher.publishEvent(new MessageSentEvent(
                    message.getId(),
                    message.getConversationId(),
                    message.getSenderId(),
                    getRecipientId(existingConversation, command.getUserId()),
                    message.getContent()
            ));

            return existingConversation;
        }

        // Crear nueva conversación con las entidades User completas
        Conversation conversation = new Conversation();
        conversation.setUser1(user1);  // ← Usar setUser1() en lugar de setUser1Id()
        conversation.setUser2(user2);  // ← Usar setUser2() en lugar de setUser2Id()
        conversation.setPublicationId(command.getPublicationId());
        conversation.setLastMessageAt(LocalDateTime.now());
        conversation.setIsArchivedByUser1(false);
        conversation.setIsArchivedByUser2(false);

        conversation = conversationRepository.save(conversation);

        // Crear mensaje inicial
        Message message = createMessage(conversation, command, user1);

        // Publicar evento
        eventPublisher.publishEvent(new MessageSentEvent(
                message.getId(),
                message.getConversationId(),
                message.getSenderId(),
                command.getRecipientId(),
                message.getContent()
        ));

        return conversation;
    }

    private Message createMessage(Conversation conversation, CreateConversationCommand command, User sender) {
        Message message = new Message();
        message.setConversation(conversation);
        message.setSender(sender);
        message.setContent(command.getInitialMessage());
        message.setMessageType(MessageType.TEXT);
        message.setStatus(MessageStatus.SENT);
        message.setCreatedAt(LocalDateTime.now());
        message.setUpdatedAt(LocalDateTime.now());

        return messageRepository.save(message);
    }

    private Long getRecipientId(Conversation conversation, Long senderId) {
        return conversation.getUser1Id().equals(senderId)
                ? conversation.getUser2Id()
                : conversation.getUser1Id();
    }
}