package com.zentry.zentrystore.api.controller.messaging;

import com.zentry.zentrystore.application.messaging.command.CreateConversationCommand;
import com.zentry.zentrystore.application.messaging.command.DeleteConversationCommand;
import com.zentry.zentrystore.application.messaging.command.MarkConversationAsArchivedCommand;
import com.zentry.zentrystore.application.messaging.command.CreateConversationCommandHandler;
import com.zentry.zentrystore.application.messaging.command.DeleteConversationCommandHandler;
import com.zentry.zentrystore.application.messaging.command.MarkConversationAsArchivedCommandHandler;
import com.zentry.zentrystore.application.messaging.dto.ConversationDTO;
import com.zentry.zentrystore.application.messaging.dto.CreateConversationRequest;
import com.zentry.zentrystore.application.messaging.dto.MessageDTO;
import com.zentry.zentrystore.application.messaging.mapper.MessagingMapper;
import com.zentry.zentrystore.application.messaging.query.GetConversationBetweenUsersQuery;
import com.zentry.zentrystore.application.messaging.query.GetConversationByIdQuery;
import com.zentry.zentrystore.application.messaging.query.GetConversationMessagesQuery;
import com.zentry.zentrystore.application.messaging.query.GetUserConversationsQuery;
import com.zentry.zentrystore.application.messaging.query.GetConversationBetweenUsersQueryHandler;
import com.zentry.zentrystore.application.messaging.query.GetConversationByIdQueryHandler;
import com.zentry.zentrystore.application.messaging.query.GetConversationMessagesQueryHandler;
import com.zentry.zentrystore.application.messaging.query.GetUserConversationsQueryHandler;
import com.zentry.zentrystore.domain.messaging.model.Conversation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversations")
@CrossOrigin(origins = "*")
public class ConversationController {

    private final CreateConversationCommandHandler createConversationCommandHandler;
    private final DeleteConversationCommandHandler deleteConversationCommandHandler;
    private final MarkConversationAsArchivedCommandHandler markConversationAsArchivedCommandHandler;
    private final GetUserConversationsQueryHandler getUserConversationsQueryHandler;
    private final GetConversationByIdQueryHandler getConversationByIdQueryHandler;
    private final GetConversationBetweenUsersQueryHandler getConversationBetweenUsersQueryHandler;
    private final GetConversationMessagesQueryHandler getConversationMessagesQueryHandler;
    private final MessagingMapper messagingMapper;

    public ConversationController(
            CreateConversationCommandHandler createConversationCommandHandler,
            DeleteConversationCommandHandler deleteConversationCommandHandler,
            MarkConversationAsArchivedCommandHandler markConversationAsArchivedCommandHandler,
            GetUserConversationsQueryHandler getUserConversationsQueryHandler,
            GetConversationByIdQueryHandler getConversationByIdQueryHandler,
            GetConversationBetweenUsersQueryHandler getConversationBetweenUsersQueryHandler,
            GetConversationMessagesQueryHandler getConversationMessagesQueryHandler,
            MessagingMapper messagingMapper) {
        this.createConversationCommandHandler = createConversationCommandHandler;
        this.deleteConversationCommandHandler = deleteConversationCommandHandler;
        this.markConversationAsArchivedCommandHandler = markConversationAsArchivedCommandHandler;
        this.getUserConversationsQueryHandler = getUserConversationsQueryHandler;
        this.getConversationByIdQueryHandler = getConversationByIdQueryHandler;
        this.getConversationBetweenUsersQueryHandler = getConversationBetweenUsersQueryHandler;
        this.getConversationMessagesQueryHandler = getConversationMessagesQueryHandler;
        this.messagingMapper = messagingMapper;
    }

    /**
     * POST /api/conversations
     * Crear nueva conversación
     */
    @PostMapping
    public ResponseEntity<ConversationDTO> createConversation(
            @Valid @RequestBody CreateConversationRequest request) {

        Long currentUserId = request.getCurrentUserId();

        CreateConversationCommand command = new CreateConversationCommand(
                currentUserId,
                request.getRecipientId(),
                request.getPublicationId(),
                request.getInitialMessage()
        );

        Conversation conversation = createConversationCommandHandler.handle(command);
        ConversationDTO dto = messagingMapper.toConversationDTO(conversation, currentUserId);

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    /**
     * GET /api/conversations
     * Obtener todas las conversaciones del usuario autenticado
     */
    @GetMapping
    public ResponseEntity<List<ConversationDTO>> getUserConversations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam Long currentUserId) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("lastMessageAt").descending());
        GetUserConversationsQuery query = new GetUserConversationsQuery(currentUserId, pageable);

        Page<Conversation> conversationsPage = getUserConversationsQueryHandler.handle(query);
        List<ConversationDTO> dtos = messagingMapper.toConversationDTOList(
                conversationsPage.getContent(),
                currentUserId
        );

        return ResponseEntity.ok(dtos);
    }

    /**
     * GET /api/conversations/{id}
     * Obtener conversación por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ConversationDTO> getConversationById(
            @PathVariable Long id,
            @RequestParam Long currentUserId) {

        GetConversationByIdQuery query = new GetConversationByIdQuery(id, currentUserId);
        ConversationDTO dto = getConversationByIdQueryHandler.handle(query);

        return ResponseEntity.ok(dto);
    }

    /**
     * GET /api/conversations/between
     * Obtener conversación entre dos usuarios específicos
     */
    @GetMapping("/between")
    public ResponseEntity<ConversationDTO> getConversationBetweenUsers(
            @RequestParam Long otherUserId,
            @RequestParam Long currentUserId) {

        GetConversationBetweenUsersQuery query = new GetConversationBetweenUsersQuery(
                currentUserId,
                otherUserId
        );

        ConversationDTO dto = getConversationBetweenUsersQueryHandler.handle(query);

        if (dto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(dto);
    }

    /**
     * GET /api/conversations/{id}/messages
     * Obtener mensajes de una conversación (paginados)
     */
    @GetMapping("/{id}/messages")
    public ResponseEntity<List<MessageDTO>> getConversationMessages(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam Long currentUserId) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());
        GetConversationMessagesQuery query = new GetConversationMessagesQuery(
                id,
                currentUserId,
                pageable
        );

        List<MessageDTO> dtos = getConversationMessagesQueryHandler.handle(query);

        return ResponseEntity.ok(dtos);
    }

    /**
     * PATCH /api/conversations/{id}/archive
     * Archivar/desarchivar conversación
     */
    @PatchMapping("/{id}/archive")
    public ResponseEntity<Void> archiveConversation(
            @PathVariable Long id,
            @RequestParam(defaultValue = "true") boolean archive,
            @RequestParam Long currentUserId) {

        MarkConversationAsArchivedCommand command = new MarkConversationAsArchivedCommand(
                id,
                currentUserId,
                archive
        );

        markConversationAsArchivedCommandHandler.handle(command);

        return ResponseEntity.noContent().build();
    }

    /**
     * DELETE /api/conversations/{id}
     * Eliminar conversación (soft delete)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConversation(
            @PathVariable Long id,
            @RequestParam Long currentUserId) {

        DeleteConversationCommand command = new DeleteConversationCommand(id, currentUserId);
        deleteConversationCommandHandler.handle(command);

        return ResponseEntity.noContent().build();
    }
}