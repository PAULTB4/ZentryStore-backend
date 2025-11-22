package com.zentry.zentrystore.application.publication.query;

import com.zentry.zentrystore.application.publication.dto.PublicationDTO;
import com.zentry.zentrystore.domain.publication.model.Publication;
import com.zentry.zentrystore.domain.publication.repository.PublicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetPublicationsByUserQueryHandler {

    private final PublicationRepository publicationRepository;

    public GetPublicationsByUserQueryHandler(PublicationRepository publicationRepository) {
        this.publicationRepository = publicationRepository;
    }

    public List<PublicationDTO> handle(GetPublicationsByUserQuery query) {
        List<Publication> publications = publicationRepository.findByUserId(query.getUserId());

        // TODO: Mapear a DTOs cuando tengamos mapper
        return null;
    }
}