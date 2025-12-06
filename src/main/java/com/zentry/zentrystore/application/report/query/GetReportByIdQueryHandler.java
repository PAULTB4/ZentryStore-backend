package com.zentry.zentrystore.application.report.query;

import com.zentry.zentrystore.application.report.dto.ReportDTO;
import com.zentry.zentrystore.application.report.mapper.ReportMapper;
import com.zentry.zentrystore.domain.report.model.Report;
import com.zentry.zentrystore.domain.report.repository.ReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetReportByIdQueryHandler {

    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;

    public GetReportByIdQueryHandler(
            ReportRepository reportRepository,
            ReportMapper reportMapper) {
        this.reportRepository = reportRepository;
        this.reportMapper = reportMapper;
    }

    public ReportDTO handle(GetReportByIdQuery query) {
        Report report = reportRepository.findById(query.getReportId())
                .orElseThrow(() -> new IllegalArgumentException("Report not found"));

        return reportMapper.toDTO(report);
    }
}