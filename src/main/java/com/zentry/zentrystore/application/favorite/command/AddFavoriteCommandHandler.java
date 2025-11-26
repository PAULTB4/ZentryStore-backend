package com.zentry.zentrystore.application.favorite.command;

import com.zentry.zentrystore.application.favorite.dto.FavoriteDTO;
import com.zentry.zentrystore.application.favorite.mapper.FavoriteMapper;
import com.zentry.zentrystore.domain.favorite.model.Favorite;
import com.zentry.zentrystore.domain.favorite.repository.FavoriteRepository;
import com.zentry.zentrystore.domain.publication.exception.PublicationNotFoundException;
import com.zentry.zentrystore.domain.publication.model.Publication;
import com.zentry.zentrystore.domain.publication.repository.PublicationRepository;
import com.zentry.zentrystore.domain.user.exception.UserNotFoundException;
import com.zentry.zentrystore.domain.user.model.User;
import com.zentry.zentrystore.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddFavoriteCommandHandler {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final PublicationRepository publicationRepository;
    private final FavoriteMapper favoriteMapper;

    public AddFavoriteCommandHandler(
            FavoriteRepository favoriteRepository,
            UserRepository userRepository,
            PublicationRepository publicationRepository,
            FavoriteMapper favoriteMapper) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.publicationRepository = publicationRepository;
        this.favoriteMapper = favoriteMapper;
    }

    @Transactional
    public FavoriteDTO handle(AddFavoriteCommand command) {
        // Validar usuario
        User user = userRepository.findById(command.getUserId())
                .orElseThrow(() -> UserNotFoundException.byId(command.getUserId()));

        // Validar publicación
        Publication publication = publicationRepository.findById(command.getPublicationId())
                .orElseThrow(() -> PublicationNotFoundException.byId(command.getPublicationId()));

        // Validar que no exista ya como favorito
        if (favoriteRepository.existsByUserIdAndPublicationId(command.getUserId(), command.getPublicationId())) {
            throw new IllegalArgumentException("Publication is already in favorites");
        }

        // Crear favorito
        Favorite favorite = new Favorite(user, publication);

        // Incrementar contador en la publicación
        publication.incrementFavoriteCount();
        publicationRepository.save(publication);

        // Guardar favorito
        Favorite savedFavorite = favoriteRepository.save(favorite);

        // TODO: Publicar evento FavoriteAddedEvent

        // Retornar DTO mapeado
        return favoriteMapper.toDTO(savedFavorite);
    }
}