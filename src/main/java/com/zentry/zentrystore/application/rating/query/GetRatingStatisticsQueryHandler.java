package com.zentry.zentrystore.application.rating.query;


import com.zentry.zentrystore.domain.rating.repository.RatingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class GetRatingStatisticsQueryHandler {

    private final RatingRepository ratingRepository;

    public GetRatingStatisticsQueryHandler(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public Map<String, Object> handle(GetRatingStatisticsQuery query) {
        Map<String, Object> statistics = new HashMap<>();

        // Total de calificaciones
        Long totalRatings = ratingRepository.countVisibleRatingsByPublication(query.getPublicationId());
        statistics.put("totalRatings", totalRatings);

        // Promedio
        Double averageScore = ratingRepository.getAverageScoreByPublication(query.getPublicationId());
        statistics.put("averageScore", averageScore != null ? averageScore : 0.0);

        // Distribución por puntuación
        List<Object[]> distribution = ratingRepository.getScoreDistributionByPublication(query.getPublicationId());
        Map<Integer, Long> scoreDistribution = new HashMap<>();
        for (Object[] row : distribution) {
            scoreDistribution.put((Integer) row[0], (Long) row[1]);
        }
        statistics.put("scoreDistribution", scoreDistribution);

        // Calificaciones verificadas
        Long verifiedRatings = ratingRepository.countVerifiedPurchasesByPublication(query.getPublicationId());
        statistics.put("verifiedRatings", verifiedRatings);

        return statistics;
    }
}