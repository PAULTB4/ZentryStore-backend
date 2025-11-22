package com.zentry.zentrystore.domain.favorite.repository;

import com.zentry.zentrystore.domain.favorite.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    // Búsquedas por usuario
    List<Favorite> findByUserId(Long userId);

    List<Favorite> findByUserIdOrderByCreatedAtDesc(Long userId);

    @Query("SELECT f FROM Favorite f WHERE f.user.id = :userId " +
            "ORDER BY f.createdAt DESC")
    List<Favorite> findFavoritesByUser(@Param("userId") Long userId);

    Long countByUserId(Long userId);

    // Búsquedas por publicación
    List<Favorite> findByPublicationId(Long publicationId);

    List<Favorite> findByPublicationIdOrderByCreatedAtDesc(Long publicationId);

    Long countByPublicationId(Long publicationId);

    // Verificar si existe favorito
    Optional<Favorite> findByUserIdAndPublicationId(Long userId, Long publicationId);

    boolean existsByUserIdAndPublicationId(Long userId, Long publicationId);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Favorite f " +
            "WHERE f.user.id = :userId AND f.publication.id = :publicationId")
    boolean isPublicationFavoritedByUser(@Param("userId") Long userId,
                                         @Param("publicationId") Long publicationId);

    // Favoritos con notificaciones habilitadas
    List<Favorite> findByUserIdAndNotificationEnabledTrue(Long userId);

    @Query("SELECT f FROM Favorite f WHERE f.publication.id = :publicationId " +
            "AND f.notificationEnabled = true")
    List<Favorite> findUsersWithNotificationsEnabled(@Param("publicationId") Long publicationId);

    @Query("SELECT COUNT(f) FROM Favorite f WHERE f.user.id = :userId " +
            "AND f.notificationEnabled = true")
    Long countFavoritesWithNotificationsEnabled(@Param("userId") Long userId);

    // Favoritos con notas
    @Query("SELECT f FROM Favorite f WHERE f.user.id = :userId " +
            "AND f.notes IS NOT NULL AND f.notes != ''")
    List<Favorite> findFavoritesWithNotes(@Param("userId") Long userId);

    @Query("SELECT COUNT(f) FROM Favorite f WHERE f.user.id = :userId " +
            "AND f.notes IS NOT NULL AND f.notes != ''")
    Long countFavoritesWithNotesByUser(@Param("userId") Long userId);

    // Búsquedas por categoría de publicación
    @Query("SELECT f FROM Favorite f WHERE f.user.id = :userId " +
            "AND f.publication.category.id = :categoryId")
    List<Favorite> findByUserAndCategory(@Param("userId") Long userId,
                                         @Param("categoryId") Long categoryId);

    @Query("SELECT COUNT(f) FROM Favorite f WHERE f.user.id = :userId " +
            "AND f.publication.category.id = :categoryId")
    Long countByUserAndCategory(@Param("userId") Long userId,
                                @Param("categoryId") Long categoryId);

    // Favoritos por estado de publicación
    @Query("SELECT f FROM Favorite f WHERE f.user.id = :userId " +
            "AND f.publication.status = 'ACTIVE'")
    List<Favorite> findActivePublicationFavorites(@Param("userId") Long userId);

    @Query("SELECT f FROM Favorite f WHERE f.user.id = :userId " +
            "AND f.publication.status = 'SOLD'")
    List<Favorite> findSoldPublicationFavorites(@Param("userId") Long userId);

    @Query("SELECT COUNT(f) FROM Favorite f WHERE f.user.id = :userId " +
            "AND f.publication.status = 'ACTIVE'")
    Long countActivePublicationFavorites(@Param("userId") Long userId);

    // Búsquedas por fecha
    List<Favorite> findByUserIdAndCreatedAtAfter(Long userId, LocalDateTime date);

    List<Favorite> findByUserIdAndCreatedAtBetween(Long userId,
                                                   LocalDateTime startDate,
                                                   LocalDateTime endDate);

    @Query("SELECT f FROM Favorite f WHERE f.user.id = :userId " +
            "AND f.createdAt >= :since ORDER BY f.createdAt DESC")
    List<Favorite> findRecentFavorites(@Param("userId") Long userId,
                                       @Param("since") LocalDateTime since);

    // Favoritos más recientes
    @Query("SELECT f FROM Favorite f WHERE f.user.id = :userId " +
            "ORDER BY f.createdAt DESC LIMIT :limit")
    List<Favorite> findRecentFavoritesByUser(@Param("userId") Long userId,
                                             @Param("limit") int limit);

    // Publicaciones más favoreadas
    @Query("SELECT f.publication.id, COUNT(f) FROM Favorite f " +
            "GROUP BY f.publication.id ORDER BY COUNT(f) DESC")
    List<Object[]> findMostFavoritedPublications();

    @Query("SELECT f.publication.id, COUNT(f) FROM Favorite f " +
            "WHERE f.publication.category.id = :categoryId " +
            "GROUP BY f.publication.id ORDER BY COUNT(f) DESC")
    List<Object[]> findMostFavoritedPublicationsByCategory(@Param("categoryId") Long categoryId);

    // Estadísticas de usuario
    @Query("SELECT f.publication.category.name, COUNT(f) FROM Favorite f " +
            "WHERE f.user.id = :userId " +
            "GROUP BY f.publication.category.name ORDER BY COUNT(f) DESC")
    List<Object[]> getUserFavoriteCategoryStatistics(@Param("userId") Long userId);

    @Query("SELECT COUNT(DISTINCT f.publication.category.id) FROM Favorite f " +
            "WHERE f.user.id = :userId")
    Long countDistinctCategoriesByUser(@Param("userId") Long userId);

    // Favoritos por rango de precio
    @Query("SELECT f FROM Favorite f WHERE f.user.id = :userId " +
            "AND f.publication.price.amount >= :minPrice " +
            "AND f.publication.price.amount <= :maxPrice")
    List<Favorite> findByUserAndPriceRange(@Param("userId") Long userId,
                                           @Param("minPrice") java.math.BigDecimal minPrice,
                                           @Param("maxPrice") java.math.BigDecimal maxPrice);

    // Usuarios que han marcado como favorita una publicación específica
    @Query("SELECT f.user FROM Favorite f WHERE f.publication.id = :publicationId")
    List<com.zentry.zentrystore.domain.user.model.User> findUsersByPublication(@Param("publicationId") Long publicationId);

    // Limpieza de favoritos de publicaciones eliminadas
    @Query("DELETE FROM Favorite f WHERE f.publication.status = 'INACTIVE' " +
            "AND f.createdAt < :cutoffDate")
    void deleteInactivePublicationFavoritesOlderThan(@Param("cutoffDate") LocalDateTime cutoffDate);

    @Query("DELETE FROM Favorite f WHERE f.user.id = :userId")
    void deleteAllByUserId(@Param("userId") Long userId);

    @Query("DELETE FROM Favorite f WHERE f.publication.id = :publicationId")
    void deleteAllByPublicationId(@Param("publicationId") Long publicationId);

    // Verificaciones adicionales
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Favorite f " +
            "WHERE f.user.id = :userId")
    boolean userHasFavorites(@Param("userId") Long userId);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Favorite f " +
            "WHERE f.publication.id = :publicationId")
    boolean publicationHasFavorites(@Param("publicationId") Long publicationId);
}