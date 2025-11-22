package com.zentry.zentrystore.application.publication.command;

import com.zentry.zentrystore.application.publication.dto.PublicationDTO;
import com.zentry.zentrystore.domain.publication.exception.InvalidPublicationDataException;
import com.zentry.zentrystore.domain.publication.exception.PublicationNotFoundException;
import com.zentry.zentrystore.domain.publication.model.*;
import com.zentry.zentrystore.domain.publication.repository.CategoryRepository;
import com.zentry.zentrystore.domain.publication.repository.PublicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdatePublicationCommandHandler {

    private final PublicationRepository publicationRepository;
    private final CategoryRepository categoryRepository;

    public UpdatePublicationCommandHandler(PublicationRepository publicationRepository,
                                           CategoryRepository categoryRepository) {
        this.publicationRepository = publicationRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public PublicationDTO handle(UpdatePublicationCommand command) {
        // Buscar publicación
        Publication publication = publicationRepository.findById(command.getPublicationId())
                .orElseThrow(() -> PublicationNotFoundException.byId(command.getPublicationId()));

        // Verificar que el usuario sea el dueño
        if (!publication.getUser().getId().equals(command.getUserId())) {
            throw new IllegalArgumentException("User is not the owner of this publication");
        }

        // Verificar que se pueda editar
        if (!publication.canBeEdited()) {
            throw InvalidPublicationDataException.forInvalidStatus(
                    publication.getStatus().name(), "EDITABLE"
            );
        }

        // Actualizar campos si están presentes
        if (command.getTitle() != null) {
            publication.setTitle(command.getTitle());
        }
        if (command.getDescription() != null) {
            publication.setDescription(command.getDescription());
        }

        // Actualizar categoría
        if (command.getCategoryId() != null) {
            Category category = categoryRepository.findById(command.getCategoryId())
                    .orElseThrow(() -> InvalidPublicationDataException.forInvalidCategory());
            publication.setCategory(category);
        }

        // Actualizar precio
        if (command.getPrice() != null && command.getCurrency() != null) {
            Price price = new Price(command.getPrice(), command.getCurrency());
            publication.setPrice(price);
        }

        // Actualizar ubicación
        if (command.getCity() != null || command.getCountry() != null) {
            Location location = publication.getLocation();
            if (location == null) {
                location = new Location();
            }
            if (command.getCity() != null) location.setCity(command.getCity());
            if (command.getState() != null) location.setState(command.getState());
            if (command.getCountry() != null) location.setCountry(command.getCountry());
            if (command.getPostalCode() != null) location.setPostalCode(command.getPostalCode());
            if (command.getAddress() != null) location.setAddress(command.getAddress());
            if (command.getLatitude() != null) location.setLatitude(command.getLatitude());
            if (command.getLongitude() != null) location.setLongitude(command.getLongitude());
            publication.setLocation(location);
        }

        // Actualizar otros campos
        if (command.getCondition() != null) {
            publication.setCondition(command.getCondition());
        }
        if (command.getAvailableQuantity() != null) {
            publication.setAvailableQuantity(command.getAvailableQuantity());
        }
        if (command.getIsNegotiable() != null) {
            publication.setIsNegotiable(command.getIsNegotiable());
        }
        if (command.getAllowsShipping() != null) {
            publication.setAllowsShipping(command.getAllowsShipping());
        }

        // Guardar cambios
        Publication savedPublication = publicationRepository.save(publication);

        // TODO: Publicar evento PublicationUpdatedEvent

        // Retornar DTO
        return null; // TODO: Mapear cuando tengamos mapper
    }
}