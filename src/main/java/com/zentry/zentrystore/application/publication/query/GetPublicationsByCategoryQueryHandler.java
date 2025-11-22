package com.zentry.zentrystore.application.publication.query;

import com.zentry.zentrystore.application.publication.dto.PublicationDTO;
import com.zentry.zentrystore.domain.publication.model.Publication;
import com.zentry.zentrystore.domain.publication.model.PublicationStatus;
import com.zentry.zentrystore.domain.publication.repository.PublicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetPublicationsByCategoryQueryHandler {

    private final PublicationRepository publicationRepository;

    public GetPublicationsByCategoryQueryHandler(PublicationRepository publicationRepository) {
        this.publicationRepository = publicationRepository;
    }

    public List<PublicationDTO> handle(GetPublicationsByCategoryQuery query) {
        List<Publication> publications = publicationRepository
                .findByCategoryIdAndStatus(query.getCategoryId(), PublicationStatus.ACTIVE);

        // TODO: Mapear a DTOs cuando tengamos mapper
        return null;
    }
}