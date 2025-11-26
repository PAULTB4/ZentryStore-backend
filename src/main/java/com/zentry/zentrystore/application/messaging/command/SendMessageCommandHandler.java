package com.zentry.zentrystore.application.messaging.command;

import com.zentry.zentrystore.application.messaging.dto.MessageDTO;
import com.zentry.zentrystore.application.messaging.mapper.MessagingMapper;
import com.zentry.zentrystore.domain.messaging.model.Conversation;
import com.zentry.zentrystore.domain.messaging.model.Message;
import com.zentry.zentrystore.domain.messaging.repository.ConversationRepository;
import com.zentry.zentrystore.domain.messaging.repository.MessageRepository;
import com.zentry.zentrystore.domain.user.exception.UserNotFoundException;
import com.zentry.zentrystore.domain.user.model.User;
import com.zentry.zentrystore.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SendMessageCommandHandler {

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final MessagingMapper messagingMapper;

    public SendMessageCommandHandler(MessageRepository messageRepository,
                                     ConversationRepository conversationRepository,
                                     UserRepository userRepository,
                                     MessagingMapper messagingMapper) {
        this.messageRepository = messageRepository;
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
        this.messagingMapper = messagingMapper;
    }

    @Transactional
    public MessageDTO handle(SendMessageCommand command) {
        // Validar usuarios
        User sender = userRepository.findById(command.getSenderId())
                .orElseThrow(() -> UserNotFoundException.byId(command.getSenderId()));

        User recipient = userRepository.findById(command.getRecipientId())
                .orElseThrow(() -> UserNotFoundException.byId(command.getRecipientId()));

        // Buscar o crear conversación
        Conversation conversation = conversationRepository
                .findByBothUsers(command.getSenderId(), command.getRecipientId())
                .orElseGet(() -> {
                    Conversation newConversation = new Conversation(sender, recipient);
                    return conversationRepository.save(newConversation);
                });

        // Verificar si la conversación está bloqueada
        if (conversation.isBlockedByEitherUser()) {
            throw new IllegalStateException("Cannot send message to blocked conversation");
        }

        // Crear mensaje
        Message message = new Message(conversation, sender, command.getContent());

        if (command.getAttachmentUrl() != null) {
            message.setAttachmentUrl(command.getAttachmentUrl());
            message.setAttachmentType(command.getAttachmentType());
        }

        // Agregar mensaje a la conversación
        conversation.addMessage(message);

        // Guardar mensaje
        Message savedMessage = messageRepository.save(message);

        // TODO: Publicar evento MessageSentEvent
        // TODO: Enviar notificación al destinatario

        // Retornar DTO mapeado
        return messagingMapper.toMessageDTO(savedMessage);
    }
}