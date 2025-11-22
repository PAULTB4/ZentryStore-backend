package com.zentry.zentrystore.application.favorite.command;

import com.zentry.zentrystore.domain.favorite.model.Favorite;
import com.zentry.zentrystore.domain.favorite.repository.FavoriteRepository;
import com.zentry.zentrystore.domain.publication.exception.PublicationNotFoundException;
import com.zentry.zentrystore.domain.publication.model.Publication;
import com.zentry.zentrystore.domain.publication.repository.PublicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RemoveFavoriteCommandHandler {

    private final FavoriteRepository favoriteRepository;
    private final PublicationRepository publicationRepository;

    public RemoveFavoriteCommandHandler(FavoriteRepository favoriteRepository,
                                        PublicationRepository publicationRepository) {
        this.favoriteRepository = favoriteRepository;
        this.publicationRepository = publicationRepository;
    }

    @Transactional
    public void handle(RemoveFavoriteCommand command) {
        // Buscar favorito
        Favorite favorite = favoriteRepository
                .findByUserIdAndPublicationId(command.getUserId(), command.getPublicationId())
                .orElseThrow(() -> new IllegalArgumentException("Favorite not found"));

        // Decrementar contador en la publicación
        Publication publication = publicationRepository.findById(command.getPublicationId())
                .orElseThrow(() -> PublicationNotFoundException.byId(command.getPublicationId()));

        publication.decrementFavoriteCount();
        publicationRepository.save(publication);

        // Eliminar favorito
        favoriteRepository.delete(favorite);

        // TODO: Publicar evento FavoriteRemovedEvent
    }
}