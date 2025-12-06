package com.zentry.zentrystore.application.publication.mapper;

import com.zentry.zentrystore.application.publication.dto.*;
import com.zentry.zentrystore.domain.publication.model.Publication;
import com.zentry.zentrystore.domain.publication.model.ProductImage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PublicationMapper {

    public PublicationResponse toResponse(Publication publication) {
        PublicationResponse response = new PublicationResponse();

        response.setId(publication.getId());
        response.setTitle(publication.getTitle());
        response.setDescription(publication.getDescription());
        response.setStatus(publication.getStatus() != null ? publication.getStatus().toString() : null);
        response.setCondition(publication.getCondition());

        // User/Seller info
        if (publication.getUser() != null) {
            response.setSellerId(publication.getUser().getId());
            response.setSellerUsername(publication.getUser().getUsername());
        }

        // Category info
        if (publication.getCategory() != null) {
            response.setCategoryId(publication.getCategory().getId());
            response.setCategoryName(publication.getCategory().getName());
        }

        // Price info (embedded)
        if (publication.getPrice() != null) {
            response.setPrice(publication.getPrice().getAmount());
            response.setCurrency(publication.getPrice().getCurrency());
        }

        // Location info (embedded)
        if (publication.getLocation() != null) {
            response.setCity(publication.getLocation().getCity());
            response.setState(publication.getLocation().getState());
            response.setCountry(publication.getLocation().getCountry());
            response.setPostalCode(publication.getLocation().getPostalCode());
            response.setAddress(publication.getLocation().getAddress());
            response.setLatitude(publication.getLocation().getLatitude());
            response.setLongitude(publication.getLocation().getLongitude());
        }

        // Other fields
        response.setAvailableQuantity(publication.getAvailableQuantity());
        response.setIsNegotiable(publication.getIsNegotiable());
        response.setAllowsShipping(publication.getAllowsShipping());

        // Images
        if (publication.getImages() != null && !publication.getImages().isEmpty()) {
            response.setImageUrls(
                    publication.getImages().stream()
                            .map(ProductImage::getImageUrl)
                            .collect(Collectors.toList())
            );
        }

        // Timestamps
        response.setCreatedAt(publication.getCreatedAt());
        response.setUpdatedAt(publication.getUpdatedAt());

        return response;
    }

    public PublicationDTO toDTO(Publication publication) {
        PublicationDTO dto = new PublicationDTO();

        dto.setId(publication.getId());
        dto.setTitle(publication.getTitle());
        dto.setDescription(publication.getDescription());
        dto.setStatus(publication.getStatus() != null ? publication.getStatus().toString() : null);
        dto.setCondition(publication.getCondition());

        // User info
        if (publication.getUser() != null) {
            dto.setUserId(publication.getUser().getId());
            dto.setUsername(publication.getUser().getUsername());
        }

        // Category info
        if (publication.getCategory() != null) {
            dto.setCategoryId(publication.getCategory().getId());
            dto.setCategoryName(publication.getCategory().getName());
        }

        // Price (nested DTO)
        if (publication.getPrice() != null) {
            PriceDTO priceDTO = new PriceDTO();
            priceDTO.setAmount(publication.getPrice().getAmount());
            priceDTO.setCurrency(publication.getPrice().getCurrency());
            priceDTO.setDiscountPercentage(publication.getPrice().getDiscountPercentage());
            priceDTO.setOriginalAmount(publication.getPrice().getOriginalAmount());
            priceDTO.setHasDiscount(publication.getPrice().hasDiscount());
            dto.setPrice(priceDTO);
        }

        // Location (nested DTO)
        if (publication.getLocation() != null) {
            LocationDTO locationDTO = new LocationDTO();
            locationDTO.setCity(publication.getLocation().getCity());
            locationDTO.setState(publication.getLocation().getState());
            locationDTO.setCountry(publication.getLocation().getCountry());
            locationDTO.setPostalCode(publication.getLocation().getPostalCode());
            locationDTO.setAddress(publication.getLocation().getAddress());
            locationDTO.setLatitude(publication.getLocation().getLatitude());
            locationDTO.setLongitude(publication.getLocation().getLongitude());
            dto.setLocation(locationDTO);
        }

        // Images (nested DTOs)
        if (publication.getImages() != null && !publication.getImages().isEmpty()) {
            List<ProductImageDTO> imageDTOs = publication.getImages().stream()
                    .map(img -> {
                        ProductImageDTO imgDTO = new ProductImageDTO();
                        imgDTO.setId(img.getId());
                        imgDTO.setImageUrl(img.getImageUrl());
                        imgDTO.setThumbnailUrl(img.getThumbnailUrl());
                        imgDTO.setIsPrimary(img.getIsPrimary());
                        imgDTO.setDisplayOrder(img.getDisplayOrder());
                        imgDTO.setAltText(img.getAltText());
                        imgDTO.setFileSize(img.getFileSize());
                        imgDTO.setFormat(img.getFormat());
                        return imgDTO;
                    })
                    .collect(Collectors.toList());
            dto.setImages(imageDTOs);
        }

        // Other fields
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

        return dto;
    }

    public PublicationSummaryDTO toSummaryDto(Publication publication) {
        PublicationSummaryDTO dto = new PublicationSummaryDTO();

        dto.setId(publication.getId());
        dto.setTitle(publication.getTitle());
        dto.setCondition(publication.getCondition());

        if (publication.getPrice() != null) {
            dto.setPrice(publication.getPrice().getAmount());
            dto.setCurrency(publication.getPrice().getCurrency());
        }

        if (publication.getLocation() != null) {
            dto.setCity(publication.getLocation().getCity());
            dto.setState(publication.getLocation().getState());
        }

        if (publication.getUser() != null) {
            dto.setUsername(publication.getUser().getUsername());
        }

        if (publication.getImages() != null && !publication.getImages().isEmpty()) {
            List<String> imageUrls = publication.getImages().stream()
                    .map(ProductImage::getImageUrl)
                    .collect(Collectors.toList());
            dto.setImageUrls(imageUrls);
        }

        if (publication.getCreatedAt() != null) {
            dto.setCreatedAt(publication.getCreatedAt().toString());
        }

        return dto;
    }

}