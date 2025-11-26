package com.zentry.zentrystore.application.user.query;

import com.zentry.zentrystore.domain.publication.model.PublicationStatus;
import com.zentry.zentrystore.application.user.dto.response.UserStatisticsResponse;
import com.zentry.zentrystore.domain.publication.repository.PublicationRepository;
import com.zentry.zentrystore.domain.user.exception.UserNotFoundException;
import com.zentry.zentrystore.domain.user.model.User;
import com.zentry.zentrystore.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetUserStatisticsQueryHandler {

    private final UserRepository userRepository;
    private final PublicationRepository publicationRepository;

    public GetUserStatisticsQueryHandler(UserRepository userRepository,
                                         PublicationRepository publicationRepository) {
        this.userRepository = userRepository;
        this.publicationRepository = publicationRepository;
    }

    public UserStatisticsResponse handle(GetUserStatisticsQuery query) {
        User user = userRepository.findById(query.getUserId())
                .orElseThrow(() -> UserNotFoundException.byId(query.getUserId()));

        // Contar publicaciones del usuario
        Long totalPublications = publicationRepository.countByUserId(user.getId());
        Long activePublications = publicationRepository.countByUserIdAndStatus(user.getId(), PublicationStatus.ACTIVE);
        UserStatisticsResponse response = new UserStatisticsResponse();
        response.setUserId(user.getId());
        response.setUsername(user.getUsername());
        response.setTotalPublications(totalPublications);
        response.setActivePublications(activePublications);
        response.setMemberSince(user.getCreatedAt());
        response.setLastLoginAt(user.getLastLoginAt());

        return response;
    }
}