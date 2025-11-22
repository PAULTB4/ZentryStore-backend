package com.zentry.zentrystore.application.favorite.mapper;

import com.zentry.zentrystore.application.favorite.dto.FavoriteDTO;
import com.zentry.zentrystore.domain.favorite.model.Favorite;
import com.zentry.zentrystore.domain.publication.model.Publication;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FavoriteMapper {

    public FavoriteDTO toDTO(Favorite favorite) {
        if (favorite == null) {
            return null;
        }

        FavoriteDTO dto = new FavoriteDTO();
        dto.setId(favorite.getId());
        dto.setUserId(favorite.getUser().getId());
        dto.setPublicationId(favorite.getPublication().getId());
        dto.setCreatedAt(favorite.getCreatedAt());

        // Mapear información de la publicación
        Publication publication = favorite.getPublication();
        if (publication != null) {
            dto.setPublicationTitle(publication.getTitle());
            dto.setPublicationStatus(publication.getStatus().name());

            if (publication.getPrice() != null) {
                dto.setPublicationPrice(publication.getPrice().getAmount().doubleValue());
            }

            if (publication.getLocation() != null) {
                dto.setPublicationCity(publication.getLocation().getCity());
            }

            // Obtener imagen principal
            if (publication.getImages() != null && !publication.getImages().isEmpty()) {
                publication.getImages().stream()
                        .filter(img -> Boolean.TRUE.equals(img.isPrimary()))
                        .findFirst()
                        .ifPresent(img -> dto.setPublicationImageUrl(img.getImageUrl()));

                // Si no hay imagen primaria, usar la primera
                if (dto.getPublicationImageUrl() == null && !publication.getImages().isEmpty()) {
                    dto.setPublicationImageUrl(publication.getImages().get(0).getImageUrl());
                }
            }
        }

        return dto;
    }

    public List<FavoriteDTO> toDTOList(List<Favorite> favorites) {
        if (favorites == null) {
            return null;
        }

        return favorites.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}