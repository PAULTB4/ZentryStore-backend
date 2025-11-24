package com.zentry.zentrystore.application.user.query;

import com.zentry.zentrystore.application.user.dto.response.UserResponse;
import com.zentry.zentrystore.application.user.mapper.UserMapper;
import com.zentry.zentrystore.domain.user.model.User;
import com.zentry.zentrystore.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class SearchUsersQueryHandler {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public SearchUsersQueryHandler(UserRepository userRepository,
                                   UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserResponse> handle(SearchUsersQuery query) {
        List<User> users = userRepository.searchByUsernameOrEmail(query.getSearchTerm());
        return users.stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }
}