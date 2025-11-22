package com.zentry.zentrystore.application.rating.command;


import com.zentry.zentrystore.application.rating.dto.RatingDTO;
import com.zentry.zentrystore.domain.publication.exception.PublicationNotFoundException;
import com.zentry.zentrystore.domain.publication.model.Publication;
import com.zentry.zentrystore.domain.publication.repository.PublicationRepository;
import com.zentry.zentrystore.domain.rating.model.Rating;
import com.zentry.zentrystore.domain.rating.model.RatingScore;
import com.zentry.zentrystore.domain.rating.repository.RatingRepository;
import com.zentry.zentrystore.domain.user.exception.UserNotFoundException;
import com.zentry.zentrystore.domain.user.model.User;
import com.zentry.zentrystore.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateRatingCommandHandler {

    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final PublicationRepository publicationRepository;

    public CreateRatingCommandHandler(RatingRepository ratingRepository,
                                      UserRepository userRepository,
                                      PublicationRepository publicationRepository) {
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.publicationRepository = publicationRepository;
    }

    @Transactional
    public RatingDTO handle(CreateRatingCommand command) {
        // Validar usuario calificador
        User rater = userRepository.findById(command.getRaterId())
                .orElseThrow(() -> UserNotFoundException.byId(command.getRaterId()));

        // Validar publicación
        Publication publication = publicationRepository.findById(command.getPublicationId())
                .orElseThrow(() -> PublicationNotFoundException.byId(command.getPublicationId()));

        // Validar que el usuario no califique su propia publicación
        if (publication.getUser().getId().equals(command.getRaterId())) {
            throw new IllegalArgumentException("Cannot rate your own publication");
        }

        // Validar que no exista una calificación previa
        if (ratingRepository.existsByRaterIdAndPublicationId(command.getRaterId(), command.getPublicationId())) {
            throw new IllegalArgumentException("User already rated this publication");
        }

        // Crear score
        RatingScore score = new RatingScore(
                command.getOverallScore(),
                command.getCommunicationScore(),
                command.getProductQualityScore(),
                command.getDeliveryScore(),
                command.getValueForMoneyScore()
        );

        // Crear rating
        Rating rating = new Rating(
                rater,
                publication.getUser(),
                publication,
                score,
                command.getComment()
        );

        if (Boolean.TRUE.equals(command.getIsAnonymous())) {
            rating.setIsAnonymous(true);
        }

        // Guardar rating
        Rating savedRating = ratingRepository.save(rating);

        // TODO: Publicar evento RatingCreatedEvent
        // TODO: Enviar notificación al usuario calificado

        // Retornar DTO
        return null; // TODO: Mapear cuando tengamos mapper
    }
}