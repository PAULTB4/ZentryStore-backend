package com.zentry.zentrystore.application.publication.query;

import com.zentry.zentrystore.application.publication.dto.PublicationResponse;
import com.zentry.zentrystore.application.publication.mapper.PublicationMapper;
import com.zentry.zentrystore.domain.publication.model.Publication;
import com.zentry.zentrystore.domain.publication.repository.PublicationRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class GetRecentPublicationsQueryHandler {

    private final PublicationRepository publicationRepository;
    private final PublicationMapper publicationMapper;

    public GetRecentPublicationsQueryHandler(PublicationRepository publicationRepository,
                                             PublicationMapper publicationMapper) {
        this.publicationRepository = publicationRepository;
        this.publicationMapper = publicationMapper;
    }

    public List<PublicationResponse> handle(GetRecentPublicationsQuery query) {
        List<Publication> publications = publicationRepository.findRecentPublications(
                PageRequest.of(0, query.getLimit())
        );

        return publications.stream()
                .map(publicationMapper::toResponse)
                .collect(Collectors.toList());
    }
}