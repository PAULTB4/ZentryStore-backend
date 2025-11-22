package com.zentry.zentrystore.application.publication.query;

import com.zentry.zentrystore.application.publication.dto.PublicationDTO;
import com.zentry.zentrystore.domain.publication.exception.PublicationNotFoundException;
import com.zentry.zentrystore.domain.publication.model.Publication;
import com.zentry.zentrystore.domain.publication.repository.PublicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetPublicationByIdQueryHandler {

    private final PublicationRepository publicationRepository;

    public GetPublicationByIdQueryHandler(PublicationRepository publicationRepository) {
        this.publicationRepository = publicationRepository;
    }

    public PublicationDTO handle(GetPublicationByIdQuery query) {
        Publication publication = publicationRepository.findById(query.getPublicationId())
                .orElseThrow(() -> PublicationNotFoundException.byId(query.getPublicationId()));

        // Incrementar contador de vistas
        publication.incrementViewCount();
        publicationRepository.save(publication);

        // TODO: Mapear a DTO cuando tengamos mapper
        return null;
    }
}