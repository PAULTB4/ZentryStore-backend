package com.zentry.zentrystore.api.controller.rating;

import com.zentry.zentrystore.application.rating.command.*;
import com.zentry.zentrystore.application.rating.dto.CreateRatingRequest;
import com.zentry.zentrystore.application.rating.dto.RatingDTO;
import com.zentry.zentrystore.application.rating.dto.UpdateRatingRequest;
import com.zentry.zentrystore.application.rating.mapper.RatingMapper;
import com.zentry.zentrystore.application.rating.query.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ratings")
@CrossOrigin(origins = "*")
public class RatingController {

    private final CreateRatingCommandHandler createRatingCommandHandler;
    private final UpdateRatingCommandHandler updateRatingCommandHandler;
    private final DeleteRatingCommandHandler deleteRatingCommandHandler;
    private final MarkRatingAsHelpfulCommandHandler markRatingAsHelpfulCommandHandler;
    private final GetRatingsByUserQueryHandler getRatingsByUserQueryHandler;
    private final GetRatingsByPublicationQueryHandler getRatingsByPublicationQueryHandler;
    private final GetAverageRatingByUserQueryHandler getAverageRatingByUserQueryHandler;
    private final GetAverageRatingByPublicationQueryHandler getAverageRatingByPublicationQueryHandler;
    private final GetRatingStatisticsQueryHandler getRatingStatisticsQueryHandler;
    private final RatingMapper ratingMapper;

    public RatingController(
            CreateRatingCommandHandler createRatingCommandHandler,
            UpdateRatingCommandHandler updateRatingCommandHandler,
            DeleteRatingCommandHandler deleteRatingCommandHandler,
            MarkRatingAsHelpfulCommandHandler markRatingAsHelpfulCommandHandler,
            GetRatingsByUserQueryHandler getRatingsByUserQueryHandler,
            GetRatingsByPublicationQueryHandler getRatingsByPublicationQueryHandler,
            GetAverageRatingByUserQueryHandler getAverageRatingByUserQueryHandler,
            GetAverageRatingByPublicationQueryHandler getAverageRatingByPublicationQueryHandler,
            GetRatingStatisticsQueryHandler getRatingStatisticsQueryHandler,
            RatingMapper ratingMapper) {
        this.createRatingCommandHandler = createRatingCommandHandler;
        this.updateRatingCommandHandler = updateRatingCommandHandler;
        this.deleteRatingCommandHandler = deleteRatingCommandHandler;
        this.markRatingAsHelpfulCommandHandler = markRatingAsHelpfulCommandHandler;
        this.getRatingsByUserQueryHandler = getRatingsByUserQueryHandler;
        this.getRatingsByPublicationQueryHandler = getRatingsByPublicationQueryHandler;
        this.getAverageRatingByUserQueryHandler = getAverageRatingByUserQueryHandler;
        this.getAverageRatingByPublicationQueryHandler = getAverageRatingByPublicationQueryHandler;
        this.getRatingStatisticsQueryHandler = getRatingStatisticsQueryHandler;
        this.ratingMapper = ratingMapper;
    }

    /**
     * POST /api/ratings
     * Crear una nueva calificación
     */
    @PostMapping
    public ResponseEntity<RatingDTO> createRating(
            @Valid @RequestBody CreateRatingRequest request,
            @RequestParam Long raterId) {

        CreateRatingCommand command = new CreateRatingCommand(
                raterId,
                request.getPublicationId(),
                request.getOverallScore(),
                request.getCommunicationScore(),
                request.getProductQualityScore(),
                request.getDeliveryScore(),
                request.getValueForMoneyScore(),
                request.getComment(),
                request.getIsAnonymous()
        );

        RatingDTO rating = createRatingCommandHandler.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(rating);
    }

    /**
     * PUT /api/ratings/{id}
     * Actualizar una calificación existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<RatingDTO> updateRating(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRatingRequest request,
            @RequestParam Long userId) {

        UpdateRatingCommand command = new UpdateRatingCommand(
                id,
                userId,
                request.getOverallScore(),
                request.getCommunicationScore(),
                request.getProductQualityScore(),
                request.getDeliveryScore(),
                request.getValueForMoneyScore(),
                request.getComment()
        );

        RatingDTO rating = updateRatingCommandHandler.handle(command);
        return ResponseEntity.ok(rating);
    }

    /**
     * DELETE /api/ratings/{id}
     * Eliminar (ocultar) una calificación
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating(
            @PathVariable Long id,
            @RequestParam Long userId) {

        DeleteRatingCommand command = new DeleteRatingCommand(id, userId);
        deleteRatingCommandHandler.handle(command);

        return ResponseEntity.noContent().build();
    }

    /**
     * POST /api/ratings/{id}/helpful
     * Marcar una calificación como útil
     */
    @PostMapping("/{id}/helpful")
    public ResponseEntity<Map<String, String>> markAsHelpful(
            @PathVariable Long id,
            @RequestParam Long userId) {

        MarkRatingAsHelpfulCommand command = new MarkRatingAsHelpfulCommand(id, userId);
        markRatingAsHelpfulCommandHandler.handle(command);

        return ResponseEntity.ok(Map.of("message", "Rating marked as helpful"));
    }

    /**
     * GET /api/ratings/user/{userId}
     * Obtener calificaciones recibidas por un usuario
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RatingDTO>> getRatingsByUser(@PathVariable Long userId) {

        GetRatingsByUserQuery query = new GetRatingsByUserQuery(userId);
        List<RatingDTO> ratings = getRatingsByUserQueryHandler.handle(query);

        return ResponseEntity.ok(ratings);
    }

    /**
     * GET /api/ratings/publication/{publicationId}
     * Obtener calificaciones de una publicación
     */
    @GetMapping("/publication/{publicationId}")
    public ResponseEntity<List<RatingDTO>> getRatingsByPublication(@PathVariable Long publicationId) {

        GetRatingsByPublicationQuery query = new GetRatingsByPublicationQuery(publicationId);
        List<RatingDTO> ratings = getRatingsByPublicationQueryHandler.handle(query);

        return ResponseEntity.ok(ratings);
    }

    /**
     * GET /api/ratings/user/{userId}/average
     * Obtener promedio de calificaciones de un usuario
     */
    @GetMapping("/user/{userId}/average")
    public ResponseEntity<Map<String, Double>> getAverageRatingByUser(@PathVariable Long userId) {

        GetAverageRatingByUserQuery query = new GetAverageRatingByUserQuery(userId);
        Double average = getAverageRatingByUserQueryHandler.handle(query);

        return ResponseEntity.ok(Map.of("averageRating", average));
    }

    /**
     * GET /api/ratings/publication/{publicationId}/average
     * Obtener promedio de calificaciones de una publicación
     */
    @GetMapping("/publication/{publicationId}/average")
    public ResponseEntity<Map<String, Double>> getAverageRatingByPublication(@PathVariable Long publicationId) {

        GetAverageRatingByPublicationQuery query = new GetAverageRatingByPublicationQuery(publicationId);
        Double average = getAverageRatingByPublicationQueryHandler.handle(query);

        return ResponseEntity.ok(Map.of("averageRating", average));
    }

    /**
     * GET /api/ratings/publication/{publicationId}/statistics
     * Obtener estadísticas completas de calificaciones de una publicación
     */
    @GetMapping("/publication/{publicationId}/statistics")
    public ResponseEntity<Map<String, Object>> getRatingStatistics(@PathVariable Long publicationId) {

        GetRatingStatisticsQuery query = new GetRatingStatisticsQuery(publicationId);
        Map<String, Object> statistics = getRatingStatisticsQueryHandler.handle(query);

        return ResponseEntity.ok(statistics);
    }
}