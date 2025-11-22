package com.zentry.zentrystore.application.favorite.query;

import com.zentry.zentrystore.domain.favorite.repository.FavoriteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetFavoriteCountByPublicationQueryHandler {

    private final FavoriteRepository favoriteRepository;

    public GetFavoriteCountByPublicationQueryHandler(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public Long handle(GetFavoriteCountByPublicationQuery query) {
        return favoriteRepository.countByPublicationId(query.getPublicationId());
    }
}