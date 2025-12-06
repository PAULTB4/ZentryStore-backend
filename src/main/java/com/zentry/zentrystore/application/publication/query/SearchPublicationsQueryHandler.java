package com.zentry.zentrystore.application.publication.query;

import com.zentry.zentrystore.application.publication.dto.PublicationSummaryDTO;
import com.zentry.zentrystore.application.publication.mapper.PublicationMapper;
import com.zentry.zentrystore.domain.publication.model.Publication;
import com.zentry.zentrystore.domain.publication.repository.PublicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class SearchPublicationsQueryHandler {

    private final PublicationRepository publicationRepository;
    private final PublicationMapper publicationMapper;

    public SearchPublicationsQueryHandler(PublicationRepository publicationRepository,
                                          PublicationMapper publicationMapper) {
        this.publicationRepository = publicationRepository;
        this.publicationMapper = publicationMapper;
    }

    public List<PublicationSummaryDTO> handle(SearchPublicationsQuery query) {

        List<Publication> publications = publicationRepository.searchAdvanced(
                query.getSearchTerm(),
                query.getCategoryId(),
                query.getMinPrice(),
                query.getMaxPrice(),
                query.getCity(),
                query.getCondition(),
                query.getFreeShipping(),
                query.getSortBy(),
                query.getOrder()
        );

        return publications.stream()
                .map(publicationMapper::toSummaryDto)  // <-- NUEVO DTO
                .collect(Collectors.toList());
    }
}
