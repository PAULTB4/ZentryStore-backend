package com.zentry.zentrystore.api.controller.messaging;

import com.zentry.zentrystore.application.messaging.command.DeleteMessageCommand;
import com.zentry.zentrystore.application.messaging.command.MarkMessageAsReadCommand;
import com.zentry.zentrystore.application.messaging.command.SendMessageCommand;
import com.zentry.zentrystore.application.messaging.command.DeleteMessageCommandHandler;
import com.zentry.zentrystore.application.messaging.command.MarkMessageAsReadCommandHandler;
import com.zentry.zentrystore.application.messaging.command.SendMessageCommandHandler;
import com.zentry.zentrystore.application.messaging.dto.MessageDTO;
import com.zentry.zentrystore.application.messaging.dto.SendMessageRequest;
import com.zentry.zentrystore.application.messaging.mapper.MessagingMapper;
import com.zentry.zentrystore.application.messaging.query.GetUnreadMessagesCountQuery;
import com.zentry.zentrystore.application.messaging.query.GetUnreadMessagesCountQueryHandler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "*")
public class MessageController {

    private final SendMessageCommandHandler sendMessageCommandHandler;
    private final MarkMessageAsReadCommandHandler markMessageAsReadCommandHandler;
    private final DeleteMessageCommandHandler deleteMessageCommandHandler;
    private final GetUnreadMessagesCountQueryHandler getUnreadMessagesCountQueryHandler;
    private final MessagingMapper messagingMapper;

    public MessageController(
            SendMessageCommandHandler sendMessageCommandHandler,
            MarkMessageAsReadCommandHandler markMessageAsReadCommandHandler,
            DeleteMessageCommandHandler deleteMessageCommandHandler,
            GetUnreadMessagesCountQueryHandler getUnreadMessagesCountQueryHandler,
            MessagingMapper messagingMapper) {
        this.sendMessageCommandHandler = sendMessageCommandHandler;
        this.markMessageAsReadCommandHandler = markMessageAsReadCommandHandler;
        this.deleteMessageCommandHandler = deleteMessageCommandHandler;
        this.getUnreadMessagesCountQueryHandler = getUnreadMessagesCountQueryHandler;
        this.messagingMapper = messagingMapper;
    }

    // ✅ Solo usuarios autenticados
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<MessageDTO> sendMessage(
            @Valid @RequestBody SendMessageRequest request) {

        SendMessageCommand command = new SendMessageCommand(
                request.getSenderId(),
                request.getRecipientId(),
                request.getContent(),
                request.getAttachmentUrl(),
                request.getAttachmentType()
        );

        MessageDTO dto = sendMessageCommandHandler.handle(command);

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    // ✅ Solo usuarios autenticados
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{id}/read")
    public ResponseEntity<Void> markMessageAsRead(
            @PathVariable Long id,
            @RequestParam Long userId) {

        MarkMessageAsReadCommand command = new MarkMessageAsReadCommand(id, userId);
        markMessageAsReadCommandHandler.handle(command);

        return ResponseEntity.noContent().build();
    }

    // ✅ Solo usuarios autenticados (validación ownership en Handler)
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(
            @PathVariable Long id,
            @RequestParam Long userId) {

        DeleteMessageCommand command = new DeleteMessageCommand(id, userId);
        deleteMessageCommandHandler.handle(command);

        return ResponseEntity.noContent().build();
    }

    // ✅ Solo usuarios autenticados
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/unread/count")
    public ResponseEntity<Map<String, Long>> getUnreadMessagesCount(
            @RequestParam Long userId) {

        GetUnreadMessagesCountQuery query = new GetUnreadMessagesCountQuery(userId);
        Long count = getUnreadMessagesCountQueryHandler.handle(query);

        Map<String, Long> response = new HashMap<>();
        response.put("unreadCount", count);

        return ResponseEntity.ok(response);
    }
}