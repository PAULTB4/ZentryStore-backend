package com.zentry.zentrystore.application.publication.command;

import com.zentry.zentrystore.application.publication.dto.PublicationDTO;
import com.zentry.zentrystore.domain.publication.exception.InvalidPublicationDataException;
import com.zentry.zentrystore.domain.publication.model.*;
import com.zentry.zentrystore.domain.publication.repository.CategoryRepository;
import com.zentry.zentrystore.domain.publication.repository.PublicationRepository;
import com.zentry.zentrystore.domain.user.exception.UserNotFoundException;
import com.zentry.zentrystore.domain.user.model.User;
import com.zentry.zentrystore.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreatePublicationCommandHandler {

    private final PublicationRepository publicationRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public CreatePublicationCommandHandler(PublicationRepository publicationRepository,
                                           UserRepository userRepository,
                                           CategoryRepository categoryRepository) {
        this.publicationRepository = publicationRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public PublicationDTO handle(CreatePublicationCommand command) {
        // Validar usuario
        User user = userRepository.findById(command.getUserId())
                .orElseThrow(() -> UserNotFoundException.byId(command.getUserId()));

        // Validar categoría
        Category category = categoryRepository.findById(command.getCategoryId())
                .orElseThrow(() -> InvalidPublicationDataException.forInvalidCategory());

        if (!category.getActive()) {
            throw InvalidPublicationDataException.forInvalidCategory();
        }

        // Crear publicación
        Publication publication = new Publication(
                command.getTitle(),
                command.getDescription(),
                user,
                category
        );

        // Configurar precio
        Price price = new Price(command.getPrice(), command.getCurrency());
        publication.setPrice(price);

        // Configurar ubicación
        if (command.getCity() != null || command.getCountry() != null) {
            Location location = new Location(
                    command.getCity(),
                    command.getState(),
                    command.getCountry(),
                    command.getPostalCode(),
                    command.getAddress()
            );
            location.setLatitude(command.getLatitude());
            location.setLongitude(command.getLongitude());
            publication.setLocation(location);
        }

        // Configurar otros campos
        publication.setCondition(command.getCondition());
        publication.setAvailableQuantity(command.getAvailableQuantity());
        publication.setIsNegotiable(command.getIsNegotiable());
        publication.setAllowsShipping(command.getAllowsShipping());

        // Agregar imágenes
        if (command.getImageUrls() != null && !command.getImageUrls().isEmpty()) {
            for (int i = 0; i < command.getImageUrls().size(); i++) {
                ProductImage image = new ProductImage(command.getImageUrls().get(i));
                image.setDisplayOrder(i);
                if (i == 0) {
                    image.setAsPrimary();
                }
                publication.addImage(image);
            }
        }

        // Guardar publicación
        Publication savedPublication = publicationRepository.save(publication);

        // TODO: Publicar evento PublicationCreatedEvent

        // Retornar DTO
        return null; // TODO: Mapear cuando tengamos mapper
    }
}