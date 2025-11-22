package com.zentry.zentrystore.domain.report.repository;

import com.zentry.zentrystore.domain.report.model.Report;
import com.zentry.zentrystore.domain.report.model.ReportStatus;
import com.zentry.zentrystore.domain.report.model.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    // Búsquedas por reportero
    List<Report> findByReporterId(Long reporterId);

    List<Report> findByReporterIdOrderByCreatedAtDesc(Long reporterId);

    Long countByReporterId(Long reporterId);

    // Búsquedas por entidad reportada
    List<Report> findByReportedEntityIdAndReportedEntityType(Long entityId, String entityType);

    @Query("SELECT r FROM Report r WHERE r.reportedEntityId = :entityId " +
            "AND r.reportedEntityType = :entityType ORDER BY r.createdAt DESC")
    List<Report> findReportsByEntity(@Param("entityId") Long entityId,
                                     @Param("entityType") String entityType);

    Long countByReportedEntityIdAndReportedEntityType(Long entityId, String entityType);

    // Búsquedas por tipo de entidad
    List<Report> findByReportedEntityType(String entityType);

    List<Report> findByReportedEntityTypeOrderByCreatedAtDesc(String entityType);

    Long countByReportedEntityType(String entityType);

    // Búsquedas por estado
    List<Report> findByStatus(String status);

    List<Report> findByStatusOrderByCreatedAtDesc(String status);

    @Query("SELECT r FROM Report r WHERE r.status = 'PENDING' " +
            "ORDER BY r.priority DESC, r.createdAt ASC")
    List<Report> findPendingReportsOrderedByPriority();

    @Query("SELECT r FROM Report r WHERE r.status = 'UNDER_REVIEW' " +
            "ORDER BY r.createdAt ASC")
    List<Report> findUnderReviewReports();

    Long countByStatus(String status);

    // Búsquedas por razón
    List<Report> findByReason(String reason);

    List<Report> findByReasonAndStatus(String reason, String status);

    Long countByReason(String reason);

    @Query("SELECT r.reason, COUNT(r) FROM Report r GROUP BY r.reason ORDER BY COUNT(r) DESC")
    List<Object[]> getReportReasonStatistics();

    // Búsquedas por prioridad
    List<Report> findByPriority(String priority);

    @Query("SELECT r FROM Report r WHERE r.priority IN ('HIGH', 'URGENT') " +
            "AND r.status IN ('PENDING', 'UNDER_REVIEW') " +
            "ORDER BY r.priority DESC, r.createdAt ASC")
    List<Report> findHighPriorityUnresolvedReports();

    @Query("SELECT r FROM Report r WHERE r.priority = 'URGENT' " +
            "AND r.status = 'PENDING'")
    List<Report> findUrgentPendingReports();

    Long countByPriority(String priority);

    // Búsquedas por revisor
    List<Report> findByReviewedById(Long reviewerId);

    List<Report> findByReviewedByIdOrderByReviewedAtDesc(Long reviewerId);

    @Query("SELECT COUNT(r) FROM Report r WHERE r.reviewedBy.id = :reviewerId " +
            "AND r.status = 'RESOLVED'")
    Long countResolvedReportsByReviewer(@Param("reviewerId") Long reviewerId);

    // Reportes no revisados
    @Query("SELECT r FROM Report r WHERE r.reviewedBy IS NULL " +
            "ORDER BY r.priority DESC, r.createdAt ASC")
    List<Report> findUnreviewedReports();

    @Query("SELECT COUNT(r) FROM Report r WHERE r.reviewedBy IS NULL")
    Long countUnreviewedReports();

    // Búsquedas por fecha
    List<Report> findByCreatedAtAfter(LocalDateTime date);

    List<Report> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Report> findByReviewedAtAfter(LocalDateTime date);

    List<Report> findByReviewedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT r FROM Report r WHERE r.createdAt >= :since " +
            "AND r.status = 'PENDING' ORDER BY r.createdAt ASC")
    List<Report> findPendingReportsSince(@Param("since") LocalDateTime since);

    // Estadísticas generales
    @Query("SELECT r.status, COUNT(r) FROM Report r GROUP BY r.status")
    List<Object[]> getReportStatusStatistics();

    @Query("SELECT r.reportedEntityType, COUNT(r) FROM Report r " +
            "GROUP BY r.reportedEntityType ORDER BY COUNT(r) DESC")
    List<Object[]> getReportsByEntityTypeStatistics();

    @Query("SELECT r.priority, COUNT(r) FROM Report r WHERE r.status IN ('PENDING', 'UNDER_REVIEW') " +
            "GROUP BY r.priority")
    List<Object[]> getUnresolvedReportsByPriority();

    // Reportes de entidades específicas con múltiples reportes
    @Query("SELECT r.reportedEntityId, r.reportedEntityType, COUNT(r) FROM Report r " +
            "GROUP BY r.reportedEntityId, r.reportedEntityType HAVING COUNT(r) > :threshold " +
            "ORDER BY COUNT(r) DESC")
    List<Object[]> findEntitiesWithMultipleReports(@Param("threshold") Long threshold);

    @Query("SELECT r.reportedEntityId FROM Report r WHERE r.reportedEntityType = :entityType " +
            "GROUP BY r.reportedEntityId HAVING COUNT(r) >= :count")
    List<Long> findEntityIdsWithMinReportCount(@Param("entityType") String entityType,
                                               @Param("count") Long count);

    // En lugar del método problemático, usa:
    @Query("SELECT r FROM Report r WHERE r.status = 'RESOLVED' AND r.reviewedAt IS NOT NULL")
    List<Report> findResolvedReportsWithReviewDate();

    // Verificaciones
    boolean existsByReporterIdAndReportedEntityIdAndReportedEntityType(Long reporterId,
                                                                       Long entityId,
                                                                       String entityType);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Report r " +
            "WHERE r.reportedEntityId = :entityId AND r.reportedEntityType = :entityType " +
            "AND r.status IN ('PENDING', 'UNDER_REVIEW')")
    boolean hasActiveReports(@Param("entityId") Long entityId,
                             @Param("entityType") String entityType);

    // Limpieza
    @Query("DELETE FROM Report r WHERE r.status IN ('RESOLVED', 'REJECTED') " +
            "AND r.reviewedAt < :cutoffDate")
    void deleteResolvedReportsOlderThan(@Param("cutoffDate") LocalDateTime cutoffDate);


    // Buscar por estado
    List<Report> findByStatus(ReportStatus status);

    // Buscar por tipo de reporte
    List<Report> findByType(ReportType type);

    // Buscar por entidad reportada
    @Query("SELECT r FROM Report r WHERE r.reportedEntityType = :entityType AND r.reportedEntityId = :entityId")
    List<Report> findByReportedEntity(@Param("entityType") String entityType,
                                      @Param("entityId") Long entityId);

    // Contar por entidad reportada
    @Query("SELECT COUNT(r) FROM Report r WHERE r.reportedEntityType = :entityType AND r.reportedEntityId = :entityId")
    Long countByReportedEntity(@Param("entityType") String entityType,
                               @Param("entityId") Long entityId);

    // Verificar si existe reporte duplicado
    boolean existsByReporterIdAndReportedEntityIdAndType(Long reporterId,
                                                         Long reportedEntityId,
                                                         ReportType type);
}