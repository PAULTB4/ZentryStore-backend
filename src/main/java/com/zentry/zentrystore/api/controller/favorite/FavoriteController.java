package com.zentry.zentrystore.api.controller.favorite;

import com.zentry.zentrystore.application.favorite.command.AddFavoriteCommand;
import com.zentry.zentrystore.application.favorite.command.AddFavoriteCommandHandler;
import com.zentry.zentrystore.application.favorite.command.RemoveFavoriteCommand;
import com.zentry.zentrystore.application.favorite.command.RemoveFavoriteCommandHandler;
import com.zentry.zentrystore.application.favorite.dto.FavoriteDTO;
import com.zentry.zentrystore.application.favorite.query.CheckIfFavoriteQuery;
import com.zentry.zentrystore.application.favorite.query.CheckIfFavoriteQueryHandler;
import com.zentry.zentrystore.application.favorite.query.GetFavoriteCountByPublicationQuery;
import com.zentry.zentrystore.application.favorite.query.GetFavoriteCountByPublicationQueryHandler;
import com.zentry.zentrystore.application.favorite.query.GetUserFavoritesQuery;
import com.zentry.zentrystore.application.favorite.query.GetUserFavoritesQueryHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/favorites")
@CrossOrigin(origins = "*")
public class FavoriteController {

    private final AddFavoriteCommandHandler addFavoriteCommandHandler;
    private final RemoveFavoriteCommandHandler removeFavoriteCommandHandler;
    private final GetUserFavoritesQueryHandler getUserFavoritesQueryHandler;
    private final CheckIfFavoriteQueryHandler checkIfFavoriteQueryHandler;
    private final GetFavoriteCountByPublicationQueryHandler getFavoriteCountByPublicationQueryHandler;

    public FavoriteController(
            AddFavoriteCommandHandler addFavoriteCommandHandler,
            RemoveFavoriteCommandHandler removeFavoriteCommandHandler,
            GetUserFavoritesQueryHandler getUserFavoritesQueryHandler,
            CheckIfFavoriteQueryHandler checkIfFavoriteQueryHandler,
            GetFavoriteCountByPublicationQueryHandler getFavoriteCountByPublicationQueryHandler) {
        this.addFavoriteCommandHandler = addFavoriteCommandHandler;
        this.removeFavoriteCommandHandler = removeFavoriteCommandHandler;
        this.getUserFavoritesQueryHandler = getUserFavoritesQueryHandler;
        this.checkIfFavoriteQueryHandler = checkIfFavoriteQueryHandler;
        this.getFavoriteCountByPublicationQueryHandler = getFavoriteCountByPublicationQueryHandler;
    }

    /**
     * POST /api/favorites
     * Agregar publicación a favoritos
     */
    @PostMapping
    public ResponseEntity<FavoriteDTO> addFavorite(
            @RequestParam Long userId,
            @RequestParam Long publicationId) {

        AddFavoriteCommand command = new AddFavoriteCommand(userId, publicationId);
        FavoriteDTO favorite = addFavoriteCommandHandler.handle(command);

        return ResponseEntity.status(HttpStatus.CREATED).body(favorite);
    }

    /**
     * DELETE /api/favorites
     * Quitar publicación de favoritos
     */
    @DeleteMapping
    public ResponseEntity<Void> removeFavorite(
            @RequestParam Long userId,
            @RequestParam Long publicationId) {

        RemoveFavoriteCommand command = new RemoveFavoriteCommand(userId, publicationId);
        removeFavoriteCommandHandler.handle(command);

        return ResponseEntity.noContent().build();
    }

    /**
     * GET /api/favorites/user/{userId}
     * Obtener favoritos de un usuario
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FavoriteDTO>> getUserFavorites(@PathVariable Long userId) {

        GetUserFavoritesQuery query = new GetUserFavoritesQuery(userId);
        List<FavoriteDTO> favorites = getUserFavoritesQueryHandler.handle(query);

        return ResponseEntity.ok(favorites);
    }

    /**
     * GET /api/favorites/check
     * Verificar si una publicación es favorita del usuario
     */
    @GetMapping("/check")
    public ResponseEntity<Map<String, Boolean>> checkIfFavorite(
            @RequestParam Long userId,
            @RequestParam Long publicationId) {

        CheckIfFavoriteQuery query = new CheckIfFavoriteQuery(userId, publicationId);
        Boolean isFavorite = checkIfFavoriteQueryHandler.handle(query);

        Map<String, Boolean> response = new HashMap<>();
        response.put("isFavorite", isFavorite);

        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/favorites/publication/{publicationId}/count
     * Obtener número de usuarios que marcaron como favorita una publicación
     */
    @GetMapping("/publication/{publicationId}/count")
    public ResponseEntity<Map<String, Long>> getFavoriteCount(
            @PathVariable Long publicationId) {

        GetFavoriteCountByPublicationQuery query = new GetFavoriteCountByPublicationQuery(publicationId);
        Long count = getFavoriteCountByPublicationQueryHandler.handle(query);

        Map<String, Long> response = new HashMap<>();
        response.put("count", count);

        return ResponseEntity.ok(response);
    }
}