package com.zentry.zentrystore.application.report.query;

import com.zentry.zentrystore.application.report.dto.ReportDTO;
import com.zentry.zentrystore.domain.report.model.Report;
import com.zentry.zentrystore.domain.report.model.ReportStatus;
import com.zentry.zentrystore.domain.report.repository.ReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetReportsByStatusQueryHandler {

    private final ReportRepository reportRepository;

    public GetReportsByStatusQueryHandler(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public List<ReportDTO> handle(GetReportsByStatusQuery query) {
        ReportStatus status;
        try {
            status = ReportStatus.valueOf(query.getStatus());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid report status: " + query.getStatus());
        }

        List<Report> reports = reportRepository.findByStatus(status);

        // TODO: Mapear a DTOs cuando tengamos mapper
        return null;
    }
}
