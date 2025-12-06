package com.zentry.zentrystore.application.report.query;

import com.zentry.zentrystore.application.report.dto.ReportDTO;
import com.zentry.zentrystore.application.report.mapper.ReportMapper;
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
    private final ReportMapper reportMapper;

    public GetReportsByStatusQueryHandler(
            ReportRepository reportRepository,
            ReportMapper reportMapper) {
        this.reportRepository = reportRepository;
        this.reportMapper = reportMapper;
    }

    public List<ReportDTO> handle(GetReportsByStatusQuery query) {
        ReportStatus status;
        try {
            status = ReportStatus.valueOf(query.getStatus());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid report status: " + query.getStatus());
        }

        List<Report> reports = reportRepository.findByStatus(status);
        return reportMapper.toDTOList(reports);
    }
}