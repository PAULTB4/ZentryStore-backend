package com.zentry.zentrystore.application.rating.command;

import com.zentry.zentrystore.domain.rating.model.Rating;
import com.zentry.zentrystore.domain.rating.repository.RatingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MarkRatingAsHelpfulCommandHandler {

    private final RatingRepository ratingRepository;

    public MarkRatingAsHelpfulCommandHandler(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Transactional
    public void handle(MarkRatingAsHelpfulCommand command) {
        Rating rating = ratingRepository.findById(command.getRatingId())
                .orElseThrow(() -> new IllegalArgumentException("Rating not found"));

        // Incrementar contador de útiles
        rating.incrementHelpfulCount();
        ratingRepository.save(rating);
    }
}