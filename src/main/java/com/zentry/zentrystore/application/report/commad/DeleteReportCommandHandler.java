package com.zentry.zentrystore.application.report.commad;

import com.zentry.zentrystore.domain.report.model.Report;
import com.zentry.zentrystore.domain.report.repository.ReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteReportCommandHandler {

    private final ReportRepository reportRepository;

    public DeleteReportCommandHandler(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Transactional
    public void handle(DeleteReportCommand command) {
        // Buscar reporte
        Report report = reportRepository.findById(command.getReportId())
                .orElseThrow(() -> new IllegalArgumentException("Report not found"));

        // TODO: Validar que el usuario tenga permisos de moderador

        // Eliminar reporte
        reportRepository.delete(report);
    }
}