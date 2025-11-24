package com.zentry.zentrystore.application.publication.command;


import com.zentry.zentrystore.application.publication.dto.PublicationDTO;
import com.zentry.zentrystore.domain.publication.exception.InvalidPublicationDataException;
import com.zentry.zentrystore.domain.publication.exception.PublicationNotFoundException;
import com.zentry.zentrystore.domain.publication.model.Publication;
import com.zentry.zentrystore.domain.publication.repository.PublicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PublishPublicationCommandHandler {

    private final PublicationRepository publicationRepository;

    public PublishPublicationCommandHandler(PublicationRepository publicationRepository) {
        this.publicationRepository = publicationRepository;
    }

    @Transactional
    public PublicationDTO handle(PublishPublicationCommand command) {
        // Buscar publicación
        Publication publication = publicationRepository.findById(command.getPublicationId())
                .orElseThrow(() -> PublicationNotFoundException.byId(command.getPublicationId()));

        // Verificar que el usuario sea el dueño
        if (!publication.getUser().getId().equals(command.getUserId())) {
            throw new IllegalArgumentException("User is not the owner of this publication");
        }

        // Validar que tenga datos completos
        if (publication.getPrice() == null || publication.getPrice().getAmount() == null) {
            throw InvalidPublicationDataException.forInvalidPrice();
        }

        if (publication.getImages().isEmpty()) {
            throw InvalidPublicationDataException.forMissingImages();
        }

        // Publicar
        publication.publish();

        // Guardar cambios
        Publication savedPublication = publicationRepository.save(publication);

        // TODO: Publicar evento PublicationStatusChangedEvent

        // Retornar DTO
        return null; // TODO: Mapear cuando tengamos mapper
    }
}