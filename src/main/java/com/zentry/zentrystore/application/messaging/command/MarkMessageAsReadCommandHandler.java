package com.zentry.zentrystore.application.messaging.command;

import com.zentry.zentrystore.domain.messaging.model.Message;
import com.zentry.zentrystore.domain.messaging.repository.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MarkMessageAsReadCommandHandler {

    private final MessageRepository messageRepository;

    public MarkMessageAsReadCommandHandler(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Transactional
    public void handle(MarkMessageAsReadCommand command) {
        Message message = messageRepository.findById(command.getMessageId())
                .orElseThrow(() -> new IllegalArgumentException("Message not found"));

        // Verificar que el usuario es el receptor (no el remitente)
        if (message.isFromUser(command.getUserId())) {
            throw new IllegalArgumentException("Cannot mark own message as read");
        }

        // Marcar como leído
        if (!message.isRead()) {
            message.markAsRead();

            // Decrementar contador de no leídos en la conversación
            message.getConversation().markAsReadByUser(command.getUserId());

            messageRepository.save(message);

            // TODO: Publicar evento MessageReadEvent
        }
    }
}