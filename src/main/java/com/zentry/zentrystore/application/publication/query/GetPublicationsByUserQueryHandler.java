package com.zentry.zentrystore.application.publication.query;

import com.zentry.zentrystore.application.publication.dto.PublicationDTO;
import com.zentry.zentrystore.application.publication.mapper.PublicationMapper;
import com.zentry.zentrystore.domain.publication.model.Publication;
import com.zentry.zentrystore.domain.publication.repository.PublicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class GetPublicationsByUserQueryHandler {

    private final PublicationRepository publicationRepository;
    private final PublicationMapper publicationMapper;

    public GetPublicationsByUserQueryHandler(PublicationRepository publicationRepository,
                                             PublicationMapper publicationMapper) {
        this.publicationRepository = publicationRepository;
        this.publicationMapper = publicationMapper;
    }

    public List<PublicationDTO> handle(GetPublicationsByUserQuery query) {
        List<Publication> publications = publicationRepository.findByUserId(query.getUserId());

        return publications.stream()
                .map(publicationMapper::toDTO)
                .collect(Collectors.toList());
    }
}