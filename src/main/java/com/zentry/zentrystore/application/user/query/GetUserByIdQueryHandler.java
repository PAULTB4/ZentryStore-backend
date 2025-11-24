package com.zentry.zentrystore.application.user.query;

import com.zentry.zentrystore.application.user.dto.response.UserResponse;
import com.zentry.zentrystore.application.user.mapper.UserMapper;
import com.zentry.zentrystore.domain.user.exception.UserNotFoundException;
import com.zentry.zentrystore.domain.user.model.User;
import com.zentry.zentrystore.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetUserByIdQueryHandler {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public GetUserByIdQueryHandler(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponse handle(GetUserByIdQuery query) {
        User user = userRepository.findById(query.getUserId())
                .orElseThrow(() -> new UserNotFoundException(query.getUserId()));

        return userMapper.toResponse(user);
    }
}