package com.zentry.zentrystore.application.user.query;

import com.zentry.zentrystore.application.user.dto.UserDTO;
import com.zentry.zentrystore.domain.user.model.User;
import com.zentry.zentrystore.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SearchUsersQueryHandler {

    private final UserRepository userRepository;

    public SearchUsersQueryHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> handle(SearchUsersQuery query) {
        List<User> users = userRepository.searchUsers(query.getSearchTerm());

        // TODO: Mapear a DTOs cuando tengamos mapper
        return null;
    }
}