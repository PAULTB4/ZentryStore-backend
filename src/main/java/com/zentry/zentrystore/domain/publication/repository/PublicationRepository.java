package com.zentry.zentrystore.domain.publication.repository;

import com.zentry.zentrystore.domain.publication.model.Publication;
import com.zentry.zentrystore.domain.publication.model.PublicationStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {

    // Búsquedas por usuario
    List<Publication> findByUserId(Long userId);

    List<Publication> findByUserIdAndStatus(Long userId, PublicationStatus status);

    Long countByUserId(Long userId);

    // Búsquedas por categoría
    List<Publication> findByCategoryId(Long categoryId);

    List<Publication> findByCategoryIdAndStatus(Long categoryId, PublicationStatus status);

    Long countByCategoryId(Long categoryId);

    // Búsquedas por estado
    List<Publication> findByStatus(PublicationStatus status);

    List<Publication> findByStatusIn(List<PublicationStatus> statuses);

    @Query("SELECT p FROM Publication p WHERE p.status = 'ACTIVE'")
    List<Publication> findActivePublications();

    @Query("SELECT p FROM Publication p WHERE p.status = 'ACTIVE' AND p.expiresAt > :now")
    List<Publication> findActiveAndNotExpired(@Param("now") LocalDateTime now);

    // Búsquedas por título
    List<Publication> findByTitleContainingIgnoreCase(String title);

    @Query("SELECT p FROM Publication p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Publication> searchByTitleOrDescription(@Param("search") String search);

    // Búsquedas por precio
    @Query("SELECT p FROM Publication p WHERE p.price.amount >= :minPrice AND p.price.amount <= :maxPrice")
    List<Publication> findByPriceRange(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);

    @Query("SELECT p FROM Publication p WHERE p.price.amount >= :minPrice AND p.price.amount <= :maxPrice " +
            "AND p.status = :status")
    List<Publication> findByPriceRangeAndStatus(@Param("minPrice") BigDecimal minPrice,
                                                @Param("maxPrice") BigDecimal maxPrice,
                                                @Param("status") PublicationStatus status);

    // Búsquedas por ubicación
    @Query("SELECT p FROM Publication p WHERE p.location.city = :city")
    List<Publication> findByCity(@Param("city") String city);

    @Query("SELECT p FROM Publication p WHERE p.location.country = :country")
    List<Publication> findByCountry(@Param("country") String country);

    @Query("SELECT p FROM Publication p WHERE p.location.city = :city AND p.location.country = :country")
    List<Publication> findByCityAndCountry(@Param("city") String city, @Param("country") String country);

    // Búsquedas por condición
    List<Publication> findByCondition(String condition);

    List<Publication> findByConditionAndStatus(String condition, PublicationStatus status);

    // Publicaciones destacadas
    List<Publication> findByIsFeaturedTrue();

    @Query("SELECT p FROM Publication p WHERE p.isFeatured = true AND p.status = 'ACTIVE'")
    List<Publication> findFeaturedActivePublications();

    // Búsquedas por fecha
    List<Publication> findByCreatedAtAfter(LocalDateTime date);

    List<Publication> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Publication> findByPublishedAtAfter(LocalDateTime date);

    @Query("SELECT p FROM Publication p WHERE p.expiresAt < :now AND p.status = 'ACTIVE'")
    List<Publication> findExpiredPublications(@Param("now") LocalDateTime now);

    // Ordenamiento y paginación
    @Query("SELECT p FROM Publication p WHERE p.status = 'ACTIVE' ORDER BY p.createdAt DESC")
    List<Publication> findRecentActivePublications();

    @Query("SELECT p FROM Publication p WHERE p.status = 'ACTIVE' ORDER BY p.viewCount DESC")
    List<Publication> findMostViewedPublications();

    @Query("SELECT p FROM Publication p WHERE p.status = 'ACTIVE' ORDER BY p.favoriteCount DESC")
    List<Publication> findMostFavoritedPublications();

    // Búsquedas complejas
    @Query("SELECT p FROM Publication p WHERE p.status = 'ACTIVE' " +
            "AND p.category.id = :categoryId " +
            "AND p.price.amount >= :minPrice AND p.price.amount <= :maxPrice " +
            "AND p.location.city = :city")
    List<Publication> findByCategoryPriceRangeAndCity(@Param("categoryId") Long categoryId,
                                                      @Param("minPrice") BigDecimal minPrice,
                                                      @Param("maxPrice") BigDecimal maxPrice,
                                                      @Param("city") String city);

    @Query("SELECT p FROM Publication p WHERE p.user.id = :userId " +
            "AND p.status IN ('ACTIVE', 'PAUSED', 'SOLD') " +
            "ORDER BY p.createdAt DESC")
    List<Publication> findUserPublicationsHistory(@Param("userId") Long userId);

    // Estadísticas
    @Query("SELECT COUNT(p) FROM Publication p WHERE p.status = :status")
    Long countByStatus(@Param("status") PublicationStatus status);

    @Query("SELECT COUNT(p) FROM Publication p WHERE p.user.id = :userId AND p.status = :status")
    Long countByUserIdAndStatus(@Param("userId") Long userId, @Param("status") PublicationStatus status);

    @Query("SELECT p.category.name, COUNT(p) FROM Publication p WHERE p.status = 'ACTIVE' GROUP BY p.category.name")
    List<Object[]> countActivePublicationsByCategory();

    @Query("SELECT SUM(p.viewCount) FROM Publication p WHERE p.user.id = :userId")
    Long getTotalViewsByUserId(@Param("userId") Long userId);

    // Verificaciones
    boolean existsByUserIdAndTitle(Long userId, String title);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Publication p " +
            "WHERE p.user.id = :userId AND p.status = 'ACTIVE'")
    boolean userHasActivePublications(@Param("userId") Long userId);

    // Agregar estos métodos a PublicationRepository

    @Query("SELECT p FROM Publication p WHERE p.status = 'ACTIVE' ORDER BY p.createdAt DESC")
    List<Publication> findRecentPublications(Pageable pageable);

    @Query("SELECT p FROM Publication p WHERE p.location.city = :city AND (:state IS NULL OR p.location.state = :state) AND p.status = 'ACTIVE'")
    List<Publication> findByLocation(@Param("city") String city, @Param("state") String state);
}