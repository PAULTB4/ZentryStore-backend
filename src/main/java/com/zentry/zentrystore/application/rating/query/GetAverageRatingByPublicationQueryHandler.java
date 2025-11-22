package com.zentry.zentrystore.application.rating.query;

import com.zentry.zentrystore.domain.rating.repository.RatingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetAverageRatingByPublicationQueryHandler {

    private final RatingRepository ratingRepository;

    public GetAverageRatingByPublicationQueryHandler(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public Double handle(GetAverageRatingByPublicationQuery query) {
        Double average = ratingRepository.getAverageScoreByPublication(query.getPublicationId());
        return average != null ? average : 0.0;
    }
}