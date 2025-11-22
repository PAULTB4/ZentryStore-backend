package com.zentry.zentrystore.application.publication.command;

import com.zentry.zentrystore.application.publication.dto.PublicationDTO;
import com.zentry.zentrystore.domain.publication.exception.InvalidPublicationDataException;
import com.zentry.zentrystore.domain.publication.exception.PublicationNotFoundException;
import com.zentry.zentrystore.domain.publication.model.Publication;
import com.zentry.zentrystore.domain.publication.model.PublicationStatus;
import com.zentry.zentrystore.domain.publication.repository.PublicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChangePublicationStatusCommandHandler {

    private final PublicationRepository publicationRepository;

    public ChangePublicationStatusCommandHandler(PublicationRepository publicationRepository) {
        this.publicationRepository = publicationRepository;
    }

    @Transactional
    public PublicationDTO handle(ChangePublicationStatusCommand command) {
        // Buscar publicación
        Publication publication = publicationRepository.findById(command.getPublicationId())
                .orElseThrow(() -> PublicationNotFoundException.byId(command.getPublicationId()));

        // Verificar que el usuario sea el dueño
        if (!publication.getUser().getId().equals(command.getUserId())) {
            throw new IllegalArgumentException("User is not the owner of this publication");
        }

        PublicationStatus oldStatus = publication.getStatus();

        // Cambiar estado según el comando
        switch (command.getNewStatus().toUpperCase()) {
            case "ACTIVE":
                publication.publish();
                break;
            case "PAUSED":
                publication.pause();
                break;
            case "INACTIVE":
                publication.deactivate();
                break;
            case "SOLD":
                publication.markAsSold();
                break;
            default:
                throw InvalidPublicationDataException.forInvalidStatus(
                        oldStatus.name(), command.getNewStatus()
                );
        }

        // Guardar cambios
        Publication savedPublication = publicationRepository.save(publication);

        // TODO: Publicar evento PublicationStatusChangedEvent

        // Retornar DTO
        return null; // TODO: Mapear cuando tengamos mapper
    }
}