package com.zentry.zentrystore.application.rating.query;

import com.zentry.zentrystore.application.rating.dto.RatingDTO;
import com.zentry.zentrystore.domain.rating.model.Rating;
import com.zentry.zentrystore.domain.rating.repository.RatingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetRatingsByUserQueryHandler {

    private final RatingRepository ratingRepository;

    public GetRatingsByUserQueryHandler(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public List<RatingDTO> handle(GetRatingsByUserQuery query) {
        List<Rating> ratings = ratingRepository
                .findVisibleRatingsByRatedUser(query.getUserId());

        // TODO: Mapear a DTOs cuando tengamos mapper
        return null;
    }
}