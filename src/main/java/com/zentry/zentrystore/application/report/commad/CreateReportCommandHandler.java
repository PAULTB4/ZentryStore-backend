package com.zentry.zentrystore.application.report.commad;

import com.zentry.zentrystore.application.report.dto.ReportDTO;
import com.zentry.zentrystore.domain.report.exception.DuplicateReportException;
import com.zentry.zentrystore.domain.report.model.Report;
import com.zentry.zentrystore.domain.report.model.ReportType;
import com.zentry.zentrystore.domain.report.repository.ReportRepository;
import com.zentry.zentrystore.domain.user.exception.UserNotFoundException;
import com.zentry.zentrystore.domain.user.model.User;
import com.zentry.zentrystore.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateReportCommandHandler {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    public CreateReportCommandHandler(ReportRepository reportRepository,
                                      UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ReportDTO handle(CreateReportCommand command) {
        // Validar usuario reportador
        User reporter = userRepository.findById(command.getReporterId())
                .orElseThrow(() -> UserNotFoundException.byId(command.getReporterId()));

        // Validar tipo de reporte
        ReportType type;
        try {
            type = ReportType.valueOf(command.getReportType());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid report type: " + command.getReportType());
        }

        // Verificar que no exista un reporte duplicado (mismo reportador, entidad y tipo)
        if (reportRepository.existsByReporterIdAndReportedEntityIdAndType(
                command.getReporterId(),
                command.getReportedEntityId(),
                type)) {
            throw DuplicateReportException.forEntity(
                    command.getReportedEntityType(),
                    command.getReportedEntityId()
            );
        }

        // Crear reporte
        Report report = new Report(
                reporter,
                type,
                command.getReportedEntityType(),
                command.getReportedEntityId(),
                command.getReason(),
                command.getDescription()
        );

        // Guardar reporte
        Report savedReport = reportRepository.save(report);

        // TODO: Publicar evento ReportCreatedEvent
        // TODO: Notificar a moderadores

        // Retornar DTO
        return null; // TODO: Mapear cuando tengamos mapper
    }
}