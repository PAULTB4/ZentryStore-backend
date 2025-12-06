package com.zentry.zentrystore.api.controller.publication;

import com.zentry.zentrystore.application.publication.command.*;
import com.zentry.zentrystore.application.publication.dto.*;
import com.zentry.zentrystore.application.publication.query.*;
import com.zentry.zentrystore.domain.publication.model.ProductImage;
import com.zentry.zentrystore.domain.publication.model.Publication;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/publications")
public class PublicationController {

    // Command Handlers
    private final CreatePublicationCommandHandler createPublicationCommandHandler;
    private final UpdatePublicationCommandHandler updatePublicationCommandHandler;
    private final DeletePublicationCommandHandler deletePublicationCommandHandler;
    private final ChangePublicationStatusCommandHandler changePublicationStatusCommandHandler;

    // Query Handlers
    private final GetPublicationByIdQueryHandler getPublicationByIdQueryHandler;
    private final GetRecentPublicationsQueryHandler getRecentPublicationsQueryHandler;
    private final GetActivePublicationsQueryHandler getActivePublicationsQueryHandler;
    private final GetFeaturedPublicationsQueryHandler getFeaturedPublicationsQueryHandler;
    private final GetPublicationsByCategoryQueryHandler getPublicationsByCategoryQueryHandler;
    private final GetPublicationsByUserQueryHandler getPublicationsByUserQueryHandler;
    private final GetPublicationsByLocationQueryHandler getPublicationsByLocationQueryHandler;
    private final SearchPublicationsQueryHandler searchPublicationsQueryHandler;
    private final GetAllCategoriesQueryHandler getAllCategoriesQueryHandler;

    public PublicationController(
            CreatePublicationCommandHandler createPublicationCommandHandler,
            UpdatePublicationCommandHandler updatePublicationCommandHandler,
            DeletePublicationCommandHandler deletePublicationCommandHandler,
            ChangePublicationStatusCommandHandler changePublicationStatusCommandHandler,
            GetPublicationByIdQueryHandler getPublicationByIdQueryHandler,
            GetRecentPublicationsQueryHandler getRecentPublicationsQueryHandler,
            GetActivePublicationsQueryHandler getActivePublicationsQueryHandler,
            GetFeaturedPublicationsQueryHandler getFeaturedPublicationsQueryHandler,
            GetPublicationsByCategoryQueryHandler getPublicationsByCategoryQueryHandler,
            GetPublicationsByUserQueryHandler getPublicationsByUserQueryHandler,
            GetPublicationsByLocationQueryHandler getPublicationsByLocationQueryHandler,
            SearchPublicationsQueryHandler searchPublicationsQueryHandler,
            GetAllCategoriesQueryHandler getAllCategoriesQueryHandler) {
        this.createPublicationCommandHandler = createPublicationCommandHandler;
        this.updatePublicationCommandHandler = updatePublicationCommandHandler;
        this.deletePublicationCommandHandler = deletePublicationCommandHandler;
        this.changePublicationStatusCommandHandler = changePublicationStatusCommandHandler;
        this.getPublicationByIdQueryHandler = getPublicationByIdQueryHandler;
        this.getRecentPublicationsQueryHandler = getRecentPublicationsQueryHandler;
        this.getActivePublicationsQueryHandler = getActivePublicationsQueryHandler;
        this.getFeaturedPublicationsQueryHandler = getFeaturedPublicationsQueryHandler;
        this.getPublicationsByCategoryQueryHandler = getPublicationsByCategoryQueryHandler;
        this.getPublicationsByUserQueryHandler = getPublicationsByUserQueryHandler;
        this.getPublicationsByLocationQueryHandler = getPublicationsByLocationQueryHandler;
        this.searchPublicationsQueryHandler = searchPublicationsQueryHandler;
        this.getAllCategoriesQueryHandler = getAllCategoriesQueryHandler;
    }

    // =============================================
    // COMMANDS (POST, PUT, PATCH, DELETE)
    // =============================================

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

    public PublicationSummaryDTO toSummaryDto(Publication publication) {
        PublicationSummaryDTO dto = new PublicationSummaryDTO();
        dto.setId(publication.getId());
        dto.setTitle(publication.getTitle());
        dto.setCondition(publication.getCondition());
        dto.setPrice(publication.getPrice().getAmount());
        dto.setCurrency(publication.getPrice().getCurrency());
        dto.setCity(publication.getLocation().getCity());
        dto.setState(publication.getLocation().getState());
        dto.setUsername(publication.getUser().getUsername()); // <– importante
        dto.setImageUrls(
                publication.getImages().stream()
                        .map(ProductImage::getImageUrl)
                        .collect(Collectors.toList())
        );

        dto.setCreatedAt(publication.getCreatedAt().toString());
        return dto;
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

    // =============================================
    // QUERIES (GET)
    // =============================================

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

    @GetMapping("/active")
    public ResponseEntity<List<PublicationDTO>> getActivePublications(
            @RequestParam(defaultValue = "20") int limit) {
        GetActivePublicationsQuery query = new GetActivePublicationsQuery(limit);
        List<PublicationDTO> response = getActivePublicationsQueryHandler.handle(query);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/featured")
    public ResponseEntity<List<PublicationDTO>> getFeaturedPublications(
            @RequestParam(defaultValue = "10") int limit) {
        GetFeaturedPublicationsQuery query = new GetFeaturedPublicationsQuery(limit);
        List<PublicationDTO> response = getFeaturedPublicationsQueryHandler.handle(query);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<PublicationDTO>> getPublicationsByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "20") int limit) {
        GetPublicationsByCategoryQuery query = new GetPublicationsByCategoryQuery(categoryId, limit);
        List<PublicationDTO> response = getPublicationsByCategoryQueryHandler.handle(query);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PublicationDTO>> getPublicationsByUser(
            @PathVariable Long userId) {
        GetPublicationsByUserQuery query = new GetPublicationsByUserQuery(userId);
        List<PublicationDTO> response = getPublicationsByUserQueryHandler.handle(query);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/location")
    public ResponseEntity<List<PublicationResponse>> getPublicationsByLocation(
            @RequestParam String city,
            @RequestParam(required = false) String state) {
        GetPublicationsByLocationQuery query = new GetPublicationsByLocationQuery(city, state);
        List<PublicationResponse> response = getPublicationsByLocationQueryHandler.handle(query);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PublicationSummaryDTO>> searchPublications(
            @RequestParam String keyword,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String condition,
            @RequestParam(required = false) Boolean freeShipping,
            @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String order) {
        SearchPublicationsQuery query = new SearchPublicationsQuery(
                keyword,
                categoryId,
                minPrice,
                maxPrice,
                city,
                condition,
                freeShipping, sortBy, order
        );
        List<PublicationSummaryDTO> response = searchPublicationsQueryHandler.handle(query);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        GetAllCategoriesQuery query = new GetAllCategoriesQuery();
        List<CategoryDTO> response = getAllCategoriesQueryHandler.handle(query);
        return ResponseEntity.ok(response);
    }
}