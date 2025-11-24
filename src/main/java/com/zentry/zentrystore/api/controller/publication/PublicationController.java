package com.zentry.zentrystore.api.controller.publication;


import com.zentry.zentrystore.application.publication.command.CreatePublicationCommand;
import com.zentry.zentrystore.application.publication.command.UpdatePublicationCommand;
import com.zentry.zentrystore.application.publication.command.DeletePublicationCommand;
import com.zentry.zentrystore.application.publication.command.ChangePublicationStatusCommand;
import com.zentry.zentrystore.application.publication.command.CreatePublicationCommandHandler;
import com.zentry.zentrystore.application.publication.command.UpdatePublicationCommandHandler;
import com.zentry.zentrystore.application.publication.command.DeletePublicationCommandHandler;
import com.zentry.zentrystore.application.publication.command.ChangePublicationStatusCommandHandler;
import com.zentry.zentrystore.application.publication.dto.CreatePublicationRequest;
import com.zentry.zentrystore.application.publication.dto.PublicationDTO;
import com.zentry.zentrystore.application.publication.dto.UpdatePublicationRequest;
import com.zentry.zentrystore.application.publication.dto.PublicationResponse;
import com.zentry.zentrystore.application.publication.query.GetPublicationByIdQuery;
import com.zentry.zentrystore.application.publication.query.GetRecentPublicationsQuery;
import com.zentry.zentrystore.application.publication.query.GetPublicationByIdQueryHandler;
import com.zentry.zentrystore.application.publication.query.GetRecentPublicationsQueryHandler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publications")
public class PublicationController {

    private final CreatePublicationCommandHandler createPublicationCommandHandler;
    private final UpdatePublicationCommandHandler updatePublicationCommandHandler;
    private final DeletePublicationCommandHandler deletePublicationCommandHandler;
    private final ChangePublicationStatusCommandHandler changePublicationStatusCommandHandler;
    private final GetPublicationByIdQueryHandler getPublicationByIdQueryHandler;
    private final GetRecentPublicationsQueryHandler getRecentPublicationsQueryHandler;

    public PublicationController(
            CreatePublicationCommandHandler createPublicationCommandHandler,
            UpdatePublicationCommandHandler updatePublicationCommandHandler,
            DeletePublicationCommandHandler deletePublicationCommandHandler,
            ChangePublicationStatusCommandHandler changePublicationStatusCommandHandler,
            GetPublicationByIdQueryHandler getPublicationByIdQueryHandler,
            GetRecentPublicationsQueryHandler getRecentPublicationsQueryHandler) {
        this.createPublicationCommandHandler = createPublicationCommandHandler;
        this.updatePublicationCommandHandler = updatePublicationCommandHandler;
        this.deletePublicationCommandHandler = deletePublicationCommandHandler;
        this.changePublicationStatusCommandHandler = changePublicationStatusCommandHandler;
        this.getPublicationByIdQueryHandler = getPublicationByIdQueryHandler;
        this.getRecentPublicationsQueryHandler = getRecentPublicationsQueryHandler;
    }

    @PostMapping
    public ResponseEntity<PublicationResponse> createPublication(
            @Valid @RequestBody CreatePublicationRequest request) {
        CreatePublicationCommand command = new CreatePublicationCommand(
                request.getSellerId(),
                request.getTitle(),
                request.getDescription(),
                request.getCategoryId(),
                request.getPrice(),
                request.getCurrency(),
                request.getCondition(),
                request.getAvailableQuantity(),
                request.getIsNegotiable(),
                request.getAllowsShipping(),
                request.getCity(),
                request.getState(),
                request.getCountry(),
                request.getPostalCode(),
                request.getAddress(),
                request.getLatitude(),
                request.getLongitude(),
                request.getImageUrls()
        );

        PublicationResponse response = createPublicationCommandHandler.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicationDTO> getPublicationById(@PathVariable Long id) {
        GetPublicationByIdQuery query = new GetPublicationByIdQuery(id);
        PublicationDTO response = getPublicationByIdQueryHandler.handle(query);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<PublicationResponse>> getRecentPublications(
            @RequestParam(defaultValue = "10") int limit) {
        GetRecentPublicationsQuery query = new GetRecentPublicationsQuery(limit);
        List<PublicationResponse> response = getRecentPublicationsQueryHandler.handle(query);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublicationDTO> updatePublication(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePublicationRequest request) {
        UpdatePublicationCommand command = new UpdatePublicationCommand(
                id,
                request.getTitle(),
                request.getDescription(),
                request.getCategoryId(),
                request.getPrice(),
                request.getCurrency(),
                request.getCondition(),
                request.getAvailableQuantity(),
                request.getIsNegotiable(),
                request.getAllowsShipping(),
                request.getCity(),
                request.getState(),
                request.getCountry(),
                request.getPostalCode(),
                request.getAddress(),
                request.getLatitude(),
                request.getLongitude(),
                request.getImageUrls()
        );

        PublicationDTO response = updatePublicationCommandHandler.handle(command);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PublicationDTO> changeStatus(
            @PathVariable Long id,
            @RequestParam Long userId,
            @RequestParam String status) {
        ChangePublicationStatusCommand command = new ChangePublicationStatusCommand(id, userId, status);
        PublicationDTO response = changePublicationStatusCommandHandler.handle(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublication(@PathVariable Long id) {
        DeletePublicationCommand command = new DeletePublicationCommand(id);
        deletePublicationCommandHandler.handle(command);
        return ResponseEntity.noContent().build();
    }
}