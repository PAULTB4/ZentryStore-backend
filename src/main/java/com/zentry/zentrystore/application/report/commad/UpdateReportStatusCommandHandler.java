package com.zentry.zentrystore.application.report.commad;

import com.zentry.zentrystore.application.report.dto.ReportDTO;
import com.zentry.zentrystore.application.report.mapper.ReportMapper;
import com.zentry.zentrystore.domain.report.model.Report;
import com.zentry.zentrystore.domain.report.model.ReportStatus;
import com.zentry.zentrystore.domain.report.repository.ReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateReportStatusCommandHandler {

    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;

    public UpdateReportStatusCommandHandler(
            ReportRepository reportRepository,
            ReportMapper reportMapper) {
        this.reportRepository = reportRepository;
        this.reportMapper = reportMapper;
    }

    @Transactional
    public ReportDTO handle(UpdateReportStatusCommand command) {
        // Buscar reporte
        Report report = reportRepository.findById(command.getReportId())
                .orElseThrow(() -> new IllegalArgumentException("Report not found"));

        // TODO: Validar que el usuario tenga permisos de moderador

        // Validar estado
        ReportStatus newStatus;
        try {
            newStatus = ReportStatus.valueOf(command.getStatus());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid report status: " + command.getStatus());
        }

        // Actualizar estado según el comando
        switch (newStatus) {
            case UNDER_REVIEW:
                report.review();
                break;
            case RESOLVED:
                report.resolve(command.getModeratorNotes());
                break;
            case DISMISSED:
                report.dismiss(command.getModeratorNotes());
                break;
            default:
                throw new IllegalArgumentException("Cannot transition to status: " + newStatus);
        }

        // Guardar cambios
        Report savedReport = reportRepository.save(report);

        // TODO: Publicar evento ReportStatusChangedEvent
        // TODO: Notificar al reportador del cambio de estado

        return reportMapper.toDTO(savedReport);
    }
}