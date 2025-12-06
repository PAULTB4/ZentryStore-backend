package com.zentry.zentrystore.api.controller.report;

import com.zentry.zentrystore.application.report.commad.*;
import com.zentry.zentrystore.application.report.dto.CreateReportRequest;
import com.zentry.zentrystore.application.report.dto.ReportDTO;
import com.zentry.zentrystore.application.report.query.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {

    private final CreateReportCommandHandler createReportCommandHandler;
    private final UpdateReportStatusCommandHandler updateReportStatusCommandHandler;
    private final DeleteReportCommandHandler deleteReportCommandHandler;
    private final GetReportByIdQueryHandler getReportByIdQueryHandler;
    private final GetReportsByEntityQueryHandler getReportsByEntityQueryHandler;
    private final GetReportsByStatusQueryHandler getReportsByStatusQueryHandler;
    private final GetReportsByTypeQueryHandler getReportsByTypeQueryHandler;
    private final GetPendingReportsQueryHandler getPendingReportsQueryHandler;
    private final GetReportCountByEntityQueryHandler getReportCountByEntityQueryHandler;

    public ReportController(
            CreateReportCommandHandler createReportCommandHandler,
            UpdateReportStatusCommandHandler updateReportStatusCommandHandler,
            DeleteReportCommandHandler deleteReportCommandHandler,
            GetReportByIdQueryHandler getReportByIdQueryHandler,
            GetReportsByEntityQueryHandler getReportsByEntityQueryHandler,
            GetReportsByStatusQueryHandler getReportsByStatusQueryHandler,
            GetReportsByTypeQueryHandler getReportsByTypeQueryHandler,
            GetPendingReportsQueryHandler getPendingReportsQueryHandler,
            GetReportCountByEntityQueryHandler getReportCountByEntityQueryHandler) {
        this.createReportCommandHandler = createReportCommandHandler;
        this.updateReportStatusCommandHandler = updateReportStatusCommandHandler;
        this.deleteReportCommandHandler = deleteReportCommandHandler;
        this.getReportByIdQueryHandler = getReportByIdQueryHandler;
        this.getReportsByEntityQueryHandler = getReportsByEntityQueryHandler;
        this.getReportsByStatusQueryHandler = getReportsByStatusQueryHandler;
        this.getReportsByTypeQueryHandler = getReportsByTypeQueryHandler;
        this.getPendingReportsQueryHandler = getPendingReportsQueryHandler;
        this.getReportCountByEntityQueryHandler = getReportCountByEntityQueryHandler;
    }

    /**
     * POST /api/reports
     * Crear un nuevo reporte
     */
    @PostMapping
    public ResponseEntity<ReportDTO> createReport(
            @Valid @RequestBody CreateReportRequest request,
            @RequestParam Long reporterId) {

        CreateReportCommand command = new CreateReportCommand(
                reporterId,
                request.getReportType(),
                request.getReportedEntityType(),
                request.getReportedEntityId(),
                request.getReason(),
                request.getDescription()
        );

        ReportDTO report = createReportCommandHandler.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(report);
    }

    /**
     * GET /api/reports/{id}
     * Obtener reporte por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReportDTO> getReportById(@PathVariable Long id) {

        GetReportByIdQuery query = new GetReportByIdQuery(id);
        ReportDTO report = getReportByIdQueryHandler.handle(query);

        return ResponseEntity.ok(report);
    }

    /**
     * GET /api/reports/entity
     * Obtener reportes de una entidad específica
     */
    @GetMapping("/entity")
    public ResponseEntity<List<ReportDTO>> getReportsByEntity(
            @RequestParam String entityType,
            @RequestParam Long entityId) {

        GetReportsByEntityQuery query = new GetReportsByEntityQuery(entityType, entityId);
        List<ReportDTO> reports = getReportsByEntityQueryHandler.handle(query);

        return ResponseEntity.ok(reports);
    }

    /**
     * GET /api/reports/entity/count
     * Contar reportes de una entidad
     */
    @GetMapping("/entity/count")
    public ResponseEntity<Map<String, Long>> getReportCountByEntity(
            @RequestParam String entityType,
            @RequestParam Long entityId) {

        GetReportCountByEntityQuery query = new GetReportCountByEntityQuery(entityType, entityId);
        Long count = getReportCountByEntityQueryHandler.handle(query);

        return ResponseEntity.ok(Map.of("count", count));
    }

    /**
     * GET /api/reports/status/{status}
     * Obtener reportes por estado
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ReportDTO>> getReportsByStatus(@PathVariable String status) {

        GetReportsByStatusQuery query = new GetReportsByStatusQuery(status);
        List<ReportDTO> reports = getReportsByStatusQueryHandler.handle(query);

        return ResponseEntity.ok(reports);
    }

    /**
     * GET /api/reports/type/{type}
     * Obtener reportes por tipo
     */
    @GetMapping("/type/{type}")
    public ResponseEntity<List<ReportDTO>> getReportsByType(@PathVariable String type) {

        GetReportsByTypeQuery query = new GetReportsByTypeQuery(type);
        List<ReportDTO> reports = getReportsByTypeQueryHandler.handle(query);

        return ResponseEntity.ok(reports);
    }

    /**
     * GET /api/reports/pending
     * Obtener todos los reportes pendientes
     */
    @GetMapping("/pending")
    public ResponseEntity<List<ReportDTO>> getPendingReports() {

        GetPendingReportsQuery query = new GetPendingReportsQuery();
        List<ReportDTO> reports = getPendingReportsQueryHandler.handle(query);

        return ResponseEntity.ok(reports);
    }

    /**
     * PATCH /api/reports/{id}/status
     * Actualizar estado de un reporte
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<ReportDTO> updateReportStatus(
            @PathVariable Long id,
            @RequestParam String status,
            @RequestParam Long moderatorId,
            @RequestParam(required = false) String moderatorNotes) {

        UpdateReportStatusCommand command = new UpdateReportStatusCommand(
                id,
                moderatorId,
                status,
                moderatorNotes
        );

        ReportDTO report = updateReportStatusCommandHandler.handle(command);
        return ResponseEntity.ok(report);
    }

    /**
     * DELETE /api/reports/{id}
     * Eliminar un reporte
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(
            @PathVariable Long id,
            @RequestParam Long moderatorId) {

        DeleteReportCommand command = new DeleteReportCommand(id, moderatorId);
        deleteReportCommandHandler.handle(command);

        return ResponseEntity.noContent().build();
    }
}