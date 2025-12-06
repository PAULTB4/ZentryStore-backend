package com.zentry.zentrystore.application.rating.query;

import com.zentry.zentrystore.application.rating.dto.RatingDTO;
import com.zentry.zentrystore.application.rating.mapper.RatingMapper;
import com.zentry.zentrystore.domain.rating.model.Rating;
import com.zentry.zentrystore.domain.rating.repository.RatingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetRatingsByPublicationQueryHandler {

    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper; // ⬅️ DECLARAR

    public GetRatingsByPublicationQueryHandler(RatingRepository ratingRepository, RatingMapper ratingMapper, RatingMapper ratingMapper1) {
        this.ratingRepository = ratingRepository;
        this.ratingMapper = ratingMapper1;
    }

    public List<RatingDTO> handle(GetRatingsByPublicationQuery query) {
        List<Rating> ratings = ratingRepository
                .findVisibleRatingsByPublication(query.getPublicationId());

        return ratingMapper.toDTOList(ratings);  // ✅ AGREGAR ESTA LÍNEA
    }
}