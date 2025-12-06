package com.zentry.zentrystore.application.report.query;

import com.zentry.zentrystore.application.report.dto.ReportDTO;
import com.zentry.zentrystore.application.report.mapper.ReportMapper;
import com.zentry.zentrystore.domain.report.model.Report;
import com.zentry.zentrystore.domain.report.model.ReportType;
import com.zentry.zentrystore.domain.report.repository.ReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetReportsByTypeQueryHandler {

    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;

    public GetReportsByTypeQueryHandler(
            ReportRepository reportRepository,
            ReportMapper reportMapper) {
        this.reportRepository = reportRepository;
        this.reportMapper = reportMapper;
    }

    public List<ReportDTO> handle(GetReportsByTypeQuery query) {
        ReportType type;
        try {
            type = ReportType.valueOf(query.getReportType());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid report type: " + query.getReportType());
        }

        List<Report> reports = reportRepository.findByType(type);
        return reportMapper.toDTOList(reports);
    }
}