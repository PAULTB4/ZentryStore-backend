package com.zentry.zentrystore.application.rating.mapper;

import com.zentry.zentrystore.application.rating.dto.RatingDTO;
import com.zentry.zentrystore.application.rating.dto.RatingScoreDTO;
import com.zentry.zentrystore.domain.rating.model.Rating;
import com.zentry.zentrystore.domain.rating.model.RatingScore;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RatingMapper {

    public RatingDTO toDTO(Rating rating) {
        if (rating == null) {
            return null;
        }

        RatingDTO dto = new RatingDTO();
        dto.setId(rating.getId());
        dto.setRaterId(rating.getRater().getId());
        dto.setRaterUsername(rating.getIsAnonymous() ? "Anonymous" : rating.getRater().getUsername());
        dto.setRatedUserId(rating.getRatedUser().getId());
        dto.setRatedUsername(rating.getRatedUser().getUsername());

        if (rating.getPublication() != null) {
            dto.setPublicationId(rating.getPublication().getId());
            dto.setPublicationTitle(rating.getPublication().getTitle());
        }

        dto.setScore(toScoreDTO(rating.getScore()));
        dto.setComment(rating.getComment());
        dto.setIsAnonymous(rating.getIsAnonymous());
        dto.setIsVerifiedPurchase(rating.getIsVerifiedPurchase());
        dto.setHelpfulCount(rating.getHelpfulCount());
        dto.setReportedCount(rating.getReportedCount());
        dto.setIsVisible(rating.getIsVisible());
        dto.setCreatedAt(rating.getCreatedAt());

        return dto;
    }

    public RatingScoreDTO toScoreDTO(RatingScore score) {
        if (score == null) {
            return null;
        }

        RatingScoreDTO dto = new RatingScoreDTO();
        dto.setOverallScore(score.getOverallScore());
        dto.setCommunicationScore(score.getCommunicationScore());
        dto.setProductQualityScore(score.getProductQualityScore());
        dto.setDeliveryScore(score.getDeliveryScore());
        dto.setValueForMoneyScore(score.getValueForMoneyScore());
        dto.setAverageScore(score.getAverageScore());

        return dto;
    }

    public List<RatingDTO> toDTOList(List<Rating> ratings) {
        if (ratings == null) {
            return null;
        }

        return ratings.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}