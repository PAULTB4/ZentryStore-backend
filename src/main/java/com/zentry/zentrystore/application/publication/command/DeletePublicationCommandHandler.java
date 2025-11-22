package com.zentry.zentrystore.application.publication.command;


import com.zentry.zentrystore.domain.publication.exception.PublicationNotFoundException;
import com.zentry.zentrystore.domain.publication.model.Publication;
import com.zentry.zentrystore.domain.publication.repository.PublicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeletePublicationCommandHandler {

    private final PublicationRepository publicationRepository;

    public DeletePublicationCommandHandler(PublicationRepository publicationRepository) {
        this.publicationRepository = publicationRepository;
    }

    @Transactional
    public void handle(DeletePublicationCommand command) {
        // Buscar publicación
        Publication publication = publicationRepository.findById(command.getPublicationId())
                .orElseThrow(() -> PublicationNotFoundException.byId(command.getPublicationId()));

        // Verificar que el usuario sea el dueño
        if (!publication.getUser().getId().equals(command.getUserId())) {
            throw new IllegalArgumentException("User is not the owner of this publication");
        }

        // Soft delete - cambiar a INACTIVE
        publication.deactivate();
        publicationRepository.save(publication);

        // TODO: Publicar evento PublicationDeletedEvent

        // Nota: En producción considera soft delete en lugar de hard delete
        // publicationRepository.delete(publication); // Hard delete
    }
}