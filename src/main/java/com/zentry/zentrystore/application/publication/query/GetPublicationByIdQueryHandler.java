package com.zentry.zentrystore.application.publication.query;

import com.zentry.zentrystore.application.publication.dto.PublicationDTO;
import com.zentry.zentrystore.application.publication.mapper.PublicationMapper;
import com.zentry.zentrystore.domain.publication.exception.PublicationNotFoundException;
import com.zentry.zentrystore.domain.publication.model.Publication;
import com.zentry.zentrystore.domain.publication.repository.PublicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetPublicationByIdQueryHandler {

    private final PublicationRepository publicationRepository;
    private final PublicationMapper publicationMapper;

    public GetPublicationByIdQueryHandler(PublicationRepository publicationRepository,
                                          PublicationMapper publicationMapper) {
        this.publicationRepository = publicationRepository;
        this.publicationMapper = publicationMapper;
    }

    @Transactional
    public PublicationDTO handle(GetPublicationByIdQuery query) {
        Publication publication = publicationRepository.findById(query.getPublicationId())
                .orElseThrow(() -> PublicationNotFoundException.byId(query.getPublicationId()));

        // Incrementar contador de vistas
        publication.incrementViewCount();
        publicationRepository.save(publication);

        // Mapear a DTO usando el mapper
        return publicationMapper.toDTO(publication);
    }
}
