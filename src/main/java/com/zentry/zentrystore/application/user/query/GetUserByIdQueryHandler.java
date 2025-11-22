package com.zentry.zentrystore.application.user.query;

import com.zentry.zentrystore.application.user.dto.UserDTO;
import com.zentry.zentrystore.domain.user.exception.UserNotFoundException;
import com.zentry.zentrystore.domain.user.model.User;
import com.zentry.zentrystore.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetUserByIdQueryHandler {

    private final UserRepository userRepository;

    public GetUserByIdQueryHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO handle(GetUserByIdQuery query) {
        User user = userRepository.findById(query.getUserId())
                .orElseThrow(() -> UserNotFoundException.byId(query.getUserId()));

        // TODO: Mapear a DTO cuando tengamos mapper
        return null;
    }
}