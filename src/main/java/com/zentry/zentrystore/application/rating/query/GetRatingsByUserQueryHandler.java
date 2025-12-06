package com.zentry.zentrystore.application.rating.query;

import com.zentry.zentrystore.application.rating.dto.RatingDTO;
import com.zentry.zentrystore.application.rating.mapper.RatingMapper;
import com.zentry.zentrystore.domain.rating.model.Rating;
import com.zentry.zentrystore.domain.rating.repository.RatingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetRatingsByUserQueryHandler {

    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper; // ⬅️ 2. Declarar el campo

    public GetRatingsByUserQueryHandler(RatingRepository ratingRepository, RatingMapper ratingMapper) {
        this.ratingMapper = ratingMapper; // ⬅️ 3. Inicializar el
        this.ratingRepository = ratingRepository;
    }

    public List<RatingDTO> handle(GetRatingsByUserQuery query) {
        List<Rating> ratings = ratingRepository
                .findVisibleRatingsByRatedUser(query.getUserId());

        return ratingMapper.toDTOList(ratings);  // ✅ AGREGAR ESTA LÍNEA
    }
}