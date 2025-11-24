package com.zentry.zentrystore.application.rating.query;

import com.zentry.zentrystore.domain.rating.repository.RatingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetAverageRatingByUserQueryHandler {

    private final RatingRepository ratingRepository;

    public GetAverageRatingByUserQueryHandler(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public Double handle(GetAverageRatingByUserQuery query) {
        Double average = ratingRepository.getAverageScoreByUser(query.getUserId());
        return average != null ? average : 0.0;
    }
}