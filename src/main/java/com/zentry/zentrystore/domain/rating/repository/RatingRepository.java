package com.zentry.zentrystore.domain.rating.repository;

import com.zentry.zentrystore.domain.rating.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    // Búsquedas por publicación
    List<Rating> findByPublicationId(Long publicationId);

    List<Rating> findByPublicationIdOrderByCreatedAtDesc(Long publicationId);

    List<Rating> findByPublicationIdAndIsVisibleTrue(Long publicationId);

    @Query("SELECT r FROM Rating r WHERE r.publication.id = :publicationId " +
            "AND r.isVisible = true ORDER BY r.createdAt DESC")
    List<Rating> findVisibleRatingsByPublication(@Param("publicationId") Long publicationId);

    Long countByPublicationId(Long publicationId);

    @Query("SELECT COUNT(r) FROM Rating r WHERE r.publication.id = :publicationId " +
            "AND r.isVisible = true")
    Long countVisibleRatingsByPublication(@Param("publicationId") Long publicationId);

    // Búsquedas por calificador (rater)
    List<Rating> findByRaterId(Long raterId);

    List<Rating> findByRaterIdOrderByCreatedAtDesc(Long raterId);

    Long countByRaterId(Long raterId);

    // Búsquedas por usuario calificado (rated user)
    List<Rating> findByRatedUserId(Long ratedUserId);

    List<Rating> findByRatedUserIdOrderByCreatedAtDesc(Long ratedUserId);

    @Query("SELECT r FROM Rating r WHERE r.ratedUser.id = :userId " +
            "AND r.isVisible = true ORDER BY r.createdAt DESC")
    List<Rating> findVisibleRatingsByRatedUser(@Param("userId") Long userId);

    Long countByRatedUserId(Long ratedUserId);

    @Query("SELECT COUNT(r) FROM Rating r WHERE r.ratedUser.id = :userId " +
            "AND r.isVisible = true")
    Long countVisibleRatingsByRatedUser(@Param("userId") Long userId);

    // Verificar si ya existe una calificación
    Optional<Rating> findByRaterIdAndPublicationId(Long raterId, Long publicationId);

    boolean existsByRaterIdAndPublicationId(Long raterId, Long publicationId);

    // Búsquedas por puntuación
    @Query("SELECT r FROM Rating r WHERE r.publication.id = :publicationId " +
            "AND r.score.overallScore >= :minScore")
    List<Rating> findByPublicationAndMinScore(@Param("publicationId") Long publicationId,
                                              @Param("minScore") Integer minScore);

    @Query("SELECT r FROM Rating r WHERE r.publication.id = :publicationId " +
            "AND r.score.overallScore = :score")
    List<Rating> findByPublicationAndExactScore(@Param("publicationId") Long publicationId,
                                                @Param("score") Integer score);

    @Query("SELECT r FROM Rating r WHERE r.ratedUser.id = :userId " +
            "AND r.score.overallScore >= 4")
    List<Rating> findPositiveRatingsByUser(@Param("userId") Long userId);

    @Query("SELECT r FROM Rating r WHERE r.ratedUser.id = :userId " +
            "AND r.score.overallScore <= 2")
    List<Rating> findNegativeRatingsByUser(@Param("userId") Long userId);

    // Promedios y estadísticas
    @Query("SELECT AVG(r.score.overallScore) FROM Rating r WHERE r.publication.id = :publicationId " +
            "AND r.isVisible = true")
    Double getAverageScoreByPublication(@Param("publicationId") Long publicationId);

    @Query("SELECT AVG(r.score.overallScore) FROM Rating r WHERE r.ratedUser.id = :userId " +
            "AND r.isVisible = true")
    Double getAverageScoreByUser(@Param("userId") Long userId);

    @Query("SELECT r.score.overallScore, COUNT(r) FROM Rating r " +
            "WHERE r.publication.id = :publicationId AND r.isVisible = true " +
            "GROUP BY r.score.overallScore ORDER BY r.score.overallScore DESC")
    List<Object[]> getScoreDistributionByPublication(@Param("publicationId") Long publicationId);

    @Query("SELECT r.score.overallScore, COUNT(r) FROM Rating r " +
            "WHERE r.ratedUser.id = :userId AND r.isVisible = true " +
            "GROUP BY r.score.overallScore ORDER BY r.score.overallScore DESC")
    List<Object[]> getScoreDistributionByUser(@Param("userId") Long userId);

    // Calificaciones verificadas
    List<Rating> findByIsVerifiedPurchaseTrue();

    List<Rating> findByPublicationIdAndIsVerifiedPurchaseTrue(Long publicationId);

    @Query("SELECT COUNT(r) FROM Rating r WHERE r.publication.id = :publicationId " +
            "AND r.isVerifiedPurchase = true")
    Long countVerifiedPurchasesByPublication(@Param("publicationId") Long publicationId);

    // Calificaciones con comentarios
    @Query("SELECT r FROM Rating r WHERE r.publication.id = :publicationId " +
            "AND r.comment IS NOT NULL AND r.comment != '' AND r.isVisible = true " +
            "ORDER BY r.createdAt DESC")
    List<Rating> findRatingsWithCommentsByPublication(@Param("publicationId") Long publicationId);

    @Query("SELECT COUNT(r) FROM Rating r WHERE r.publication.id = :publicationId " +
            "AND r.comment IS NOT NULL AND r.comment != ''")
    Long countRatingsWithCommentsByPublication(@Param("publicationId") Long publicationId);

    // Calificaciones útiles (por helpful count)
    @Query("SELECT r FROM Rating r WHERE r.publication.id = :publicationId " +
            "AND r.isVisible = true ORDER BY r.helpfulCount DESC")
    List<Rating> findMostHelpfulRatingsByPublication(@Param("publicationId") Long publicationId);

    @Query("SELECT r FROM Rating r WHERE r.publication.id = :publicationId " +
            "AND r.helpfulCount >= :minHelpful AND r.isVisible = true " +
            "ORDER BY r.helpfulCount DESC")
    List<Rating> findRatingsWithMinHelpfulCount(@Param("publicationId") Long publicationId,
                                                @Param("minHelpful") Integer minHelpful);

    // Calificaciones reportadas
    List<Rating> findByReportedCountGreaterThan(Integer threshold);

    @Query("SELECT r FROM Rating r WHERE r.reportedCount >= :threshold " +
            "ORDER BY r.reportedCount DESC")
    List<Rating> findMostReportedRatings(@Param("threshold") Integer threshold);

    // Búsquedas por fecha
    List<Rating> findByCreatedAtAfter(LocalDateTime date);

    List<Rating> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT r FROM Rating r WHERE r.publication.id = :publicationId " +
            "AND r.createdAt >= :since ORDER BY r.createdAt DESC")
    List<Rating> findRecentRatingsByPublication(@Param("publicationId") Long publicationId,
                                                @Param("since") LocalDateTime since);

    // Calificaciones anónimas
    List<Rating> findByIsAnonymousTrue();

    List<Rating> findByPublicationIdAndIsAnonymousTrue(Long publicationId);

    // Visibilidad
    List<Rating> findByIsVisibleFalse();

    @Query("SELECT r FROM Rating r WHERE r.isVisible = false " +
            "ORDER BY r.reportedCount DESC")
    List<Rating> findHiddenRatings();

    // Estadísticas generales
    @Query("SELECT COUNT(r) FROM Rating r WHERE r.ratedUser.id = :userId " +
            "AND r.score.overallScore >= 4 AND r.isVisible = true")
    Long countPositiveRatingsByUser(@Param("userId") Long userId);

    @Query("SELECT COUNT(r) FROM Rating r WHERE r.ratedUser.id = :userId " +
            "AND r.score.overallScore <= 2 AND r.isVisible = true")
    Long countNegativeRatingsByUser(@Param("userId") Long userId);

    // Limpieza
    @Query("DELETE FROM Rating r WHERE r.isVisible = false AND r.createdAt < :cutoffDate")
    void deleteHiddenRatingsOlderThan(@Param("cutoffDate") LocalDateTime cutoffDate);
}