package com.zentry.zentrystore.api.controller.report;

import com.zentry.zentrystore.application.report.commad.*;
import com.zentry.zentrystore.application.report.dto.CreateReportRequest;
import com.zentry.zentrystore.application.report.dto.ReportDTO;
import com.zentry.zentrystore.application.report.query.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    // ✅ Cualquier usuario autenticado puede crear reportes
    @PreAuthorize("isAuthenticated()")
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

    // ✅ Solo ADMIN puede ver reportes
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ReportDTO> getReportById(@PathVariable Long id) {

        GetReportByIdQuery query = new GetReportByIdQuery(id);
        ReportDTO report = getReportByIdQueryHandler.handle(query);

        return ResponseEntity.ok(report);
    }

    // ✅ Solo ADMIN
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/entity")
    public ResponseEntity<List<ReportDTO>> getReportsByEntity(
            @RequestParam String entityType,
            @RequestParam Long entityId) {

        GetReportsByEntityQuery query = new GetReportsByEntityQuery(entityType, entityId);
        List<ReportDTO> reports = getReportsByEntityQueryHandler.handle(query);

        return ResponseEntity.ok(reports);
    }

    // ✅ Solo ADMIN
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/entity/count")
    public ResponseEntity<Map<String, Long>> getReportCountByEntity(
            @RequestParam String entityType,
            @RequestParam Long entityId) {

        GetReportCountByEntityQuery query = new GetReportCountByEntityQuery(entityType, entityId);
        Long count = getReportCountByEntityQueryHandler.handle(query);

        return ResponseEntity.ok(Map.of("count", count));
    }

    // ✅ Solo ADMIN
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ReportDTO>> getReportsByStatus(@PathVariable String status) {

        GetReportsByStatusQuery query = new GetReportsByStatusQuery(status);
        List<ReportDTO> reports = getReportsByStatusQueryHandler.handle(query);

        return ResponseEntity.ok(reports);
    }

    // ✅ Solo ADMIN
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/type/{type}")
    public ResponseEntity<List<ReportDTO>> getReportsByType(@PathVariable String type) {

        GetReportsByTypeQuery query = new GetReportsByTypeQuery(type);
        List<ReportDTO> reports = getReportsByTypeQueryHandler.handle(query);

        return ResponseEntity.ok(reports);
    }

    // ✅ Solo ADMIN
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/pending")
    public ResponseEntity<List<ReportDTO>> getPendingReports() {

        GetPendingReportsQuery query = new GetPendingReportsQuery();
        List<ReportDTO> reports = getPendingReportsQueryHandler.handle(query);

        return ResponseEntity.ok(reports);
    }

    // ✅ Solo ADMIN puede actualizar status de reportes
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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

    // ✅ Solo ADMIN puede eliminar reportes
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(
            @PathVariable Long id,
            @RequestParam Long moderatorId) {

        DeleteReportCommand command = new DeleteReportCommand(id, moderatorId);
        deleteReportCommandHandler.handle(command);

        return ResponseEntity.noContent().build();
    }
}