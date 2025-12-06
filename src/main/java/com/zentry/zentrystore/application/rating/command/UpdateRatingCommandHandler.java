package com.zentry.zentrystore.application.rating.command;

import com.zentry.zentrystore.application.rating.dto.RatingDTO;
import com.zentry.zentrystore.application.rating.mapper.RatingMapper;
import com.zentry.zentrystore.domain.rating.model.Rating;
import com.zentry.zentrystore.domain.rating.model.RatingScore;
import com.zentry.zentrystore.domain.rating.repository.RatingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateRatingCommandHandler {

    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper; // ⬅️ DECLARAR

    public UpdateRatingCommandHandler(RatingRepository ratingRepository, RatingMapper ratingMapper) {
        this.ratingRepository = ratingRepository;
        this.ratingMapper = ratingMapper;
    }

    @Transactional
    public RatingDTO handle(UpdateRatingCommand command) {
        Rating rating = ratingRepository.findById(command.getRatingId())
                .orElseThrow(() -> new IllegalArgumentException("Rating not found"));

        // Verificar que el usuario sea el calificador
        if (!rating.getRater().getId().equals(command.getUserId())) {
            throw new IllegalArgumentException("User is not the rater");
        }

        // Actualizar score si se proporcionan valores
        RatingScore currentScore = rating.getScore();

        if (command.getOverallScore() != null) {
            currentScore.setOverallScore(command.getOverallScore());
        }
        if (command.getCommunicationScore() != null) {
            currentScore.setCommunicationScore(command.getCommunicationScore());
        }
        if (command.getProductQualityScore() != null) {
            currentScore.setProductQualityScore(command.getProductQualityScore());
        }
        if (command.getDeliveryScore() != null) {
            currentScore.setDeliveryScore(command.getDeliveryScore());
        }
        if (command.getValueForMoneyScore() != null) {
            currentScore.setValueForMoneyScore(command.getValueForMoneyScore());
        }

        rating.updateScore(currentScore);

        // Actualizar comentario
        if (command.getComment() != null) {
            rating.updateComment(command.getComment());
        }

        // Guardar cambios
        Rating savedRating = ratingRepository.save(rating);

        // Mapear y retornar DTO
        return ratingMapper.toDTO(savedRating);  // ✅ AGREGAR ESTA LÍNEA
    }
}