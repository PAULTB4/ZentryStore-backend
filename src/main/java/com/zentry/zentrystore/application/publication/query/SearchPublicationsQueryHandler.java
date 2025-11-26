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
public class SearchPublicationsQueryHandler {

    private final PublicationRepository publicationRepository;
    private final PublicationMapper publicationMapper;

    public SearchPublicationsQueryHandler(PublicationRepository publicationRepository,
                                          PublicationMapper publicationMapper) {
        this.publicationRepository = publicationRepository;
        this.publicationMapper = publicationMapper;
    }

    public List<PublicationDTO> handle(SearchPublicationsQuery query) {
        List<Publication> publications;

        // Búsqueda compleja con filtros
        if (query.getCategoryId() != null && query.getMinPrice() != null
                && query.getMaxPrice() != null && query.getCity() != null) {

            publications = publicationRepository.findByCategoryPriceRangeAndCity(
                    query.getCategoryId(),
                    query.getMinPrice(),
                    query.getMaxPrice(),
                    query.getCity()
            );
        }
        // Búsqueda por rango de precio
        else if (query.getMinPrice() != null && query.getMaxPrice() != null) {
            publications = publicationRepository.findByPriceRange(
                    query.getMinPrice(),
                    query.getMaxPrice()
            );
        }
        // Búsqueda por texto
        else if (query.getSearchTerm() != null && !query.getSearchTerm().isEmpty()) {
            publications = publicationRepository.searchByTitleOrDescription(query.getSearchTerm());
        }
        // Búsqueda por condición
        else if (query.getCondition() != null) {
            publications = publicationRepository.findByCondition(query.getCondition());
        }
        // Publicaciones activas por defecto
        else {
            publications = publicationRepository.findActivePublications();
        }

        return publications.stream()
                .map(publicationMapper::toDTO)
                .collect(Collectors.toList());
    }
}