package com.zentry.zentrystore.domain.publication.repository;

import com.zentry.zentrystore.domain.publication.model.Publication;
import java.math.BigDecimal;
import java.util.List;

public interface PublicationRepositoryCustom {
    List<Publication> searchAdvanced(
            String searchTerm,
            Long categoryId,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            String city,
            String condition,
            Boolean freeShipping,
            String sortBy,
            String order
    );
}
