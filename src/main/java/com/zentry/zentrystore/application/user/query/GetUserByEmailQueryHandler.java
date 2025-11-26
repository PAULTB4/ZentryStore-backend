package com.zentry.zentrystore.application.user.query;

import com.zentry.zentrystore.application.user.dto.UserDTO;
import com.zentry.zentrystore.application.user.mapper.UserMapper;
import com.zentry.zentrystore.domain.user.exception.UserNotFoundException;
import com.zentry.zentrystore.domain.user.model.User;
import com.zentry.zentrystore.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetUserByEmailQueryHandler {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public GetUserByEmailQueryHandler(UserRepository userRepository,
                                      UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDTO handle(GetUserByEmailQuery query) {
        User user = userRepository.findByEmail(query.getEmail())
                .orElseThrow(() -> UserNotFoundException.byEmail(query.getEmail()));

        return userMapper.toDTO(user);
    }
}