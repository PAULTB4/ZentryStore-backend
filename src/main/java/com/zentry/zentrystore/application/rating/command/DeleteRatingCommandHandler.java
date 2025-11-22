package com.zentry.zentrystore.application.rating.command;

import com.zentry.zentrystore.domain.rating.model.Rating;
import com.zentry.zentrystore.domain.rating.repository.RatingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteRatingCommandHandler {

    private final RatingRepository ratingRepository;

    public DeleteRatingCommandHandler(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Transactional
    public void handle(DeleteRatingCommand command) {
        Rating rating = ratingRepository.findById(command.getRatingId())
                .orElseThrow(() -> new IllegalArgumentException("Rating not found"));

        // Verificar que el usuario sea el calificador
        if (!rating.getRater().getId().equals(command.getUserId())) {
            throw new IllegalArgumentException("User is not the rater");
        }

        // Ocultar rating (soft delete)
        rating.hide();
        ratingRepository.save(rating);

        // O eliminar completamente (hard delete)
        // ratingRepository.delete(rating);
    }
}