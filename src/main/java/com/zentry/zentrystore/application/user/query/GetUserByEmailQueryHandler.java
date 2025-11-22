package com.zentry.zentrystore.application.user.query;

import com.zentry.zentrystore.application.user.dto.UserDTO;
import com.zentry.zentrystore.domain.user.exception.UserNotFoundException;
import com.zentry.zentrystore.domain.user.model.User;
import com.zentry.zentrystore.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetUserByEmailQueryHandler {

    private final UserRepository userRepository;

    public GetUserByEmailQueryHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO handle(GetUserByEmailQuery query) {
        User user = userRepository.findByEmail(query.getEmail())
                .orElseThrow(() -> UserNotFoundException.byEmail(query.getEmail()));

        // TODO: Mapear a DTO cuando tengamos mapper
        return null;
    }
}