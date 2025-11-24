package com.zentry.zentrystore.application.publication.mapper;

import com.zentry.zentrystore.application.publication.dto.*;
import com.zentry.zentrystore.domain.publication.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PublicationMapper {

    public PublicationDTO toDTO(Publication publication) {
        if (publication == null) {
            return null;
        }

        PublicationDTO dto = new PublicationDTO();
        dto.setId(publication.getId());
        dto.setTitle(publication.getTitle());
        dto.setDescription(publication.getDescription());
        dto.setUserId(publication.getUser().getId());
        dto.setUsername(publication.getUser().getUsername());
        dto.setCategoryId(publication.getCategory().getId());
        dto.setCategoryName(publication.getCategory().getName());
        dto.setStatus(publication.getStatus().name());
        dto.setCondition(publication.getCondition());
        dto.setAvailableQuantity(publication.getAvailableQuantity());
        dto.setViewCount(publication.getViewCount());
        dto.setFavoriteCount(publication.getFavoriteCount());
        dto.setIsFeatured(publication.getIsFeatured());
        dto.setIsNegotiable(publication.getIsNegotiable());
        dto.setAllowsShipping(publication.getAllowsShipping());
        dto.setPublishedAt(publication.getPublishedAt());
        dto.setExpiresAt(publication.getExpiresAt());
        dto.setCreatedAt(publication.getCreatedAt());
        dto.setUpdatedAt(publication.getUpdatedAt());

        // Mapear precio
        if (publication.getPrice() != null) {
            dto.setPrice(toPriceDTO(publication.getPrice()));
        }

        // Mapear ubicación
        if (publication.getLocation() != null) {
            dto.setLocation(toLocationDTO(publication.getLocation()));
        }

        // Mapear imágenes
        if (publication.getImages() != null && !publication.getImages().isEmpty()) {
            dto.setImages(publication.getImages().stream()
                    .map(this::toProductImageDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public PriceDTO toPriceDTO(Price price) {
        if (price == null) {
            return null;
        }

        PriceDTO dto = new PriceDTO();
        dto.setAmount(price.getAmount());
        dto.setCurrency(price.getCurrency());
        dto.setOriginalAmount(price.getOriginalAmount());
        dto.setDiscountPercentage(price.getDiscountPercentage());
        dto.setHasDiscount(price.hasDiscount());

        return dto;
    }

    public LocationDTO toLocationDTO(Location location) {
        if (location == null) {
            return null;
        }

        LocationDTO dto = new LocationDTO();
        dto.setCity(location.getCity());
        dto.setState(location.getState());
        dto.setCountry(location.getCountry());
        dto.setPostalCode(location.getPostalCode());
        dto.setAddress(location.getAddress());
        dto.setLatitude(location.getLatitude());
        dto.setLongitude(location.getLongitude());

        return dto;
    }

    public ProductImageDTO toProductImageDTO(ProductImage image) {
        if (image == null) {
            return null;
        }

        ProductImageDTO dto = new ProductImageDTO();
        dto.setId(image.getId());
        dto.setImageUrl(image.getImageUrl());
        dto.setThumbnailUrl(image.getThumbnailUrl());
        dto.setIsPrimary(image.isPrimary());
        dto.setDisplayOrder(image.getDisplayOrder());
        dto.setAltText(image.getAltText());

        return dto;
    }

    public CategoryDTO toCategoryDTO(Category category) {
        if (category == null) {
            return null;
        }

        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setSlug(category.getSlug());
        dto.setDescription(category.getDescription());
        dto.setIconUrl(category.getIconUrl());
        dto.setActive(category.getActive());
        dto.setDisplayOrder(category.getDisplayOrder());
        dto.setPublicationCount((long) category.getPublicationCount());
        dto.setCreatedAt(category.getCreatedAt());

        // Mapear categoría padre si existe
        if (category.getParent() != null) {
            dto.setParentId(category.getParent().getId());
            dto.setParentName(category.getParent().getName());
        }

        return dto;
    }

    public List<PublicationDTO> toDTOList(List<Publication> publications) {
        if (publications == null) {
            return null;
        }

        return publications.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<CategoryDTO> toCategoryDTOList(List<Category> categories) {
        if (categories == null) {
            return null;
        }

        return categories.stream()
                .map(this::toCategoryDTO)
                .collect(Collectors.toList());
    }

    public PublicationResponse toResponse(Publication publication) {
        if (publication == null) {
            return null;
        }

        PublicationResponse response = new PublicationResponse();
        response.setId(publication.getId());
        response.setTitle(publication.getTitle());
        response.setDescription(publication.getDescription());

        if (publication.getStatus() != null) {
            response.setStatus(publication.getStatus().name());
        }

        // Seller info
        if (publication.getUser() != null) {
            response.setSellerId(publication.getUser().getId());
            response.setSellerUsername(publication.getUser().getUsername());
        }

        // Category info
        if (publication.getCategory() != null) {
            response.setCategoryId(publication.getCategory().getId());
            response.setCategoryName(publication.getCategory().getName());
        }

        // Price info
        if (publication.getPrice() != null) {
            response.setPrice(publication.getPrice().getAmount());
            response.setCurrency(publication.getPrice().getCurrency());
        }

        // Location info
        if (publication.getLocation() != null) {
            response.setCity(publication.getLocation().getCity());
            response.setState(publication.getLocation().getState());
            response.setCountry(publication.getLocation().getCountry());
            response.setPostalCode(publication.getLocation().getPostalCode());
            response.setAddress(publication.getLocation().getAddress());
            response.setLatitude(publication.getLocation().getLatitude());
            response.setLongitude(publication.getLocation().getLongitude());
        }

        // Images
        if (publication.getImages() != null) {
            response.setImageUrls(publication.getImages().stream()
                    .map(ProductImage::getImageUrl)
                    .collect(Collectors.toList()));
        }

        // Additional fields
        response.setCondition(publication.getCondition());
        response.setAvailableQuantity(publication.getAvailableQuantity());
        response.setIsNegotiable(publication.getIsNegotiable());
        response.setAllowsShipping(publication.getAllowsShipping());

        // Timestamps
        response.setCreatedAt(publication.getCreatedAt());
        response.setUpdatedAt(publication.getUpdatedAt());

        return response;
    }
}