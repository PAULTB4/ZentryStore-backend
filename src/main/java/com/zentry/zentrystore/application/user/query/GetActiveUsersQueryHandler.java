package com.zentry.zentrystore.application.user.query;

import com.zentry.zentrystore.application.user.dto.UserDTO;
import com.zentry.zentrystore.application.user.mapper.UserMapper;
import com.zentry.zentrystore.domain.user.model.User;
import com.zentry.zentrystore.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class GetActiveUsersQueryHandler {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public GetActiveUsersQueryHandler(UserRepository userRepository,
                                      UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserDTO> handle(GetActiveUsersQuery query) {
        List<User> users = userRepository.findByActiveTrue();

        return users.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }
}