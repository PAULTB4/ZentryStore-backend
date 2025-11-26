package com.zentry.zentrystore.application.messaging.command;

import com.zentry.zentrystore.application.messaging.command.DeleteMessageCommand;
import com.zentry.zentrystore.domain.messaging.model.Message;
import com.zentry.zentrystore.domain.messaging.repository.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class DeleteMessageCommandHandler {

    private final MessageRepository messageRepository;

    public DeleteMessageCommandHandler(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Transactional
    public void handle(DeleteMessageCommand command) {
        Message message = messageRepository.findById(command.getMessageId())
                .orElseThrow(() -> new IllegalArgumentException("Message not found"));

        // Verificar que el usuario es el remitente
        if (!message.getSenderId().equals(command.getUserId())) {
            throw new IllegalArgumentException("Only the sender can delete the message");
        }

        // Soft delete
        message.setDeletedAt(LocalDateTime.now());
        messageRepository.save(message);
    }
}