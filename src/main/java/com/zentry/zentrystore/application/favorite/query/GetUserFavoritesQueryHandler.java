package com.zentry.zentrystore.application.favorite.query;

import com.zentry.zentrystore.application.favorite.dto.FavoriteDTO;
import com.zentry.zentrystore.application.favorite.mapper.FavoriteMapper;
import com.zentry.zentrystore.domain.favorite.model.Favorite;
import com.zentry.zentrystore.domain.favorite.repository.FavoriteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetUserFavoritesQueryHandler {

    private final FavoriteRepository favoriteRepository;
    private final FavoriteMapper favoriteMapper;

    public GetUserFavoritesQueryHandler(
            FavoriteRepository favoriteRepository,
            FavoriteMapper favoriteMapper) {
        this.favoriteRepository = favoriteRepository;
        this.favoriteMapper = favoriteMapper;
    }

    public List<FavoriteDTO> handle(GetUserFavoritesQuery query) {
        List<Favorite> favorites = favoriteRepository.findByUserId(query.getUserId());
        return favoriteMapper.toDTOList(favorites);
    }
}