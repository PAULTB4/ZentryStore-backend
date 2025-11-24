package com.zentry.zentrystore.application.report.query;

import com.zentry.zentrystore.domain.report.repository.ReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetReportCountByEntityQueryHandler {

    private final ReportRepository reportRepository;

    public GetReportCountByEntityQueryHandler(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Long handle(GetReportCountByEntityQuery query) {
        return reportRepository.countByReportedEntity(
                query.getEntityType(),
                query.getEntityId()
        );
    }
}