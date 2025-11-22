package com.zentry.zentrystore.application.report.query;

import com.zentry.zentrystore.application.report.dto.ReportDTO;
import com.zentry.zentrystore.domain.report.model.Report;
import com.zentry.zentrystore.domain.report.repository.ReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetReportByIdQueryHandler {

    private final ReportRepository reportRepository;

    public GetReportByIdQueryHandler(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public ReportDTO handle(GetReportByIdQuery query) {
        Report report = reportRepository.findById(query.getReportId())
                .orElseThrow(() -> new IllegalArgumentException("Report not found"));

        // TODO: Mapear a DTO cuando tengamos mapper
        return null;
    }
}