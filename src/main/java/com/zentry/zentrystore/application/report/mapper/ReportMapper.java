package com.zentry.zentrystore.application.report.mapper;

import com.zentry.zentrystore.application.report.dto.ReportDTO;
import com.zentry.zentrystore.domain.report.model.Report;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReportMapper {

    public ReportDTO toDTO(Report report) {
        if (report == null) {
            return null;
        }

        ReportDTO dto = new ReportDTO();
        dto.setId(report.getId());
        dto.setReporterId(report.getReporter().getId());
        dto.setReporterUsername(report.getReporter().getUsername());
        dto.setReportType(report.getReason()); // En tu modelo, "reason" es el tipo de reporte
        dto.setReportedEntityType(report.getReportedEntityType());
        dto.setReportedEntityId(report.getReportedEntityId());
        dto.setReason(report.getReason());
        dto.setDescription(report.getDescription());
        dto.setStatus(report.getStatus().name());
        dto.setModeratorNotes(report.getModeratorNotes());
        dto.setResolvedAt(report.getResolvedAt());
        dto.setCreatedAt(report.getCreatedAt());

        return dto;
    }

    public List<ReportDTO> toDTOList(List<Report> reports) {
        if (reports == null) {
            return null;
        }

        return reports.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}