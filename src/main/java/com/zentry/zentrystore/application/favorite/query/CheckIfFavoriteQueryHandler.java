package com.zentry.zentrystore.application.favorite.query;

import com.zentry.zentrystore.domain.favorite.repository.FavoriteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CheckIfFavoriteQueryHandler {

    private final FavoriteRepository favoriteRepository;

    public CheckIfFavoriteQueryHandler(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public Boolean handle(CheckIfFavoriteQuery query) {
        return favoriteRepository.existsByUserIdAndPublicationId(
                query.getUserId(),
                query.getPublicationId()
        );
    }
}