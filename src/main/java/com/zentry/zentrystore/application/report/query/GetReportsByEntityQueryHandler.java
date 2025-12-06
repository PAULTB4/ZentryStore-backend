package com.zentry.zentrystore.application.report.query;

import com.zentry.zentrystore.application.report.dto.ReportDTO;
import com.zentry.zentrystore.application.report.mapper.ReportMapper;
import com.zentry.zentrystore.domain.report.model.Report;
import com.zentry.zentrystore.domain.report.repository.ReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetReportsByEntityQueryHandler {

    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;

    public GetReportsByEntityQueryHandler(
            ReportRepository reportRepository,
            ReportMapper reportMapper) {
        this.reportRepository = reportRepository;
        this.reportMapper = reportMapper;
    }

    public List<ReportDTO> handle(GetReportsByEntityQuery query) {
        List<Report> reports = reportRepository.findByReportedEntity(
                query.getEntityType(),
                query.getEntityId()
        );

        return reportMapper.toDTOList(reports);
    }
}