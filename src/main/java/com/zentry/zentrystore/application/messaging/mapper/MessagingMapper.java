package com.zentry.zentrystore.application.messaging.mapper;

import com.zentry.zentrystore.application.messaging.dto.ConversationDTO;
import com.zentry.zentrystore.application.messaging.dto.MessageDTO;
import com.zentry.zentrystore.domain.messaging.model.Conversation;
import com.zentry.zentrystore.domain.messaging.model.Message;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessagingMapper {

    public MessageDTO toMessageDTO(Message message) {
        if (message == null) {
            return null;
        }

        MessageDTO dto = new MessageDTO();
        dto.setId(message.getId());
        dto.setConversationId(message.getConversation().getId());
        dto.setSenderId(message.getSender().getId());
        dto.setSenderUsername(message.getSender().getUsername());
        dto.setContent(message.getContent());
        dto.setStatus(message.getStatus().name());
        dto.setAttachmentUrl(message.getAttachmentUrl());
        dto.setAttachmentType(message.getAttachmentType());
        dto.setIsSystemMessage(message.getIsSystemMessage());
        dto.setReadAt(message.getReadAt());
        dto.setDeliveredAt(message.getDeliveredAt());
        dto.setCreatedAt(message.getCreatedAt());

        return dto;
    }

    public ConversationDTO toConversationDTO(Conversation conversation) {
        if (conversation == null) {
            return null;
        }

        ConversationDTO dto = new ConversationDTO();
        dto.setId(conversation.getId());
        dto.setUser1Id(conversation.getUser1().getId());
        dto.setUser1Username(conversation.getUser1().getUsername());
        dto.setUser2Id(conversation.getUser2().getId());
        dto.setUser2Username(conversation.getUser2().getUsername());
        dto.setLastMessageAt(conversation.getLastMessageAt());
        // Por defecto usamos false, en la versión con userId lo calculamos correctamente
        dto.setIsArchived(false);
        dto.setIsBlocked(conversation.isBlockedByEitherUser());
        dto.setCreatedAt(conversation.getCreatedAt());

        // Mapear último mensaje si existe
        if (!conversation.getMessages().isEmpty()) {
            Message lastMessage = conversation.getMessages().get(conversation.getMessages().size() - 1);
            dto.setLastMessage(toMessageDTO(lastMessage));
        }

        // Calcular mensajes no leídos (esto dependerá del contexto del usuario)
        // Por ahora lo dejamos en 0
        dto.setUnreadCount(0);

        return dto;
    }

    public ConversationDTO toConversationDTO(Conversation conversation, Long currentUserId) {
        if (conversation == null) {
            return null;
        }

        ConversationDTO dto = toConversationDTO(conversation);

        // Calcular datos específicos del usuario actual
        if (currentUserId != null) {
            // Mensajes no leídos usando el contador de la conversación
            dto.setUnreadCount(conversation.getUnreadCountForUser(currentUserId));

            // Estado de archivado y bloqueado para este usuario
            dto.setIsArchived(conversation.isArchivedByUser(currentUserId));
            dto.setIsBlocked(conversation.isBlockedByUser(currentUserId));
        }

        return dto;
    }

    public List<MessageDTO> toMessageDTOList(List<Message> messages) {
        if (messages == null) {
            return null;
        }

        return messages.stream()
                .map(this::toMessageDTO)
                .collect(Collectors.toList());
    }

    public List<ConversationDTO> toConversationDTOList(List<Conversation> conversations) {
        if (conversations == null) {
            return null;
        }

        return conversations.stream()
                .map(this::toConversationDTO)
                .collect(Collectors.toList());
    }

    public List<ConversationDTO> toConversationDTOList(List<Conversation> conversations, Long currentUserId) {
        if (conversations == null) {
            return null;
        }

        return conversations.stream()
                .map(conversation -> toConversationDTO(conversation, currentUserId))
                .collect(Collectors.toList());
    }
}