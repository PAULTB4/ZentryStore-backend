package com.zentry.zentrystore.application.publication.query;

import com.zentry.zentrystore.application.publication.dto.PublicationDTO;
import com.zentry.zentrystore.application.publication.mapper.PublicationMapper;
import com.zentry.zentrystore.domain.publication.model.Publication;
import com.zentry.zentrystore.domain.publication.repository.PublicationRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class GetActivePublicationsQueryHandler {

    private final PublicationRepository publicationRepository;
    private final PublicationMapper publicationMapper;

    public GetActivePublicationsQueryHandler(PublicationRepository publicationRepository,
                                             PublicationMapper publicationMapper) {
        this.publicationRepository = publicationRepository;
        this.publicationMapper = publicationMapper;
    }

    public List<PublicationDTO> handle(GetActivePublicationsQuery query) {
        Pageable pageable = PageRequest.of(0, query.getLimit());
        List<Publication> publications = publicationRepository.findRecentPublications(pageable);

        return publications.stream()
                .filter(pub -> "ACTIVE".equals(pub.getStatus().toString()))
                .map(publicationMapper::toDTO)
                .collect(Collectors.toList());
    }
}