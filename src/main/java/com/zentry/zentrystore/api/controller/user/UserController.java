package com.zentry.zentrystore.api.controller.user;

import com.zentry.zentrystore.application.user.command.DeleteUserCommand;
import com.zentry.zentrystore.application.user.command.DeleteUserCommandHandler;
import com.zentry.zentrystore.application.user.dto.response.UserResponse;
import com.zentry.zentrystore.application.user.query.GetUserByIdQuery;
import com.zentry.zentrystore.application.user.query.GetUserByIdQueryHandler;
import com.zentry.zentrystore.application.user.query.SearchUsersQuery;
import com.zentry.zentrystore.application.user.query.SearchUsersQueryHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final GetUserByIdQueryHandler getUserByIdQueryHandler;
    private final SearchUsersQueryHandler searchUsersQueryHandler;
    private final DeleteUserCommandHandler deleteUserCommandHandler;

    public UserController(
            GetUserByIdQueryHandler getUserByIdQueryHandler,
            SearchUsersQueryHandler searchUsersQueryHandler,
            DeleteUserCommandHandler deleteUserCommandHandler) {
        this.getUserByIdQueryHandler = getUserByIdQueryHandler;
        this.searchUsersQueryHandler = searchUsersQueryHandler;
        this.deleteUserCommandHandler = deleteUserCommandHandler;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        GetUserByIdQuery query = new GetUserByIdQuery(id);
        UserResponse response = getUserByIdQueryHandler.handle(query);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserResponse>> searchUsers(@RequestParam String keyword) {
        SearchUsersQuery query = new SearchUsersQuery(keyword);
        List<UserResponse> response = searchUsersQueryHandler.handle(query);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        DeleteUserCommand command = new DeleteUserCommand(id);
        deleteUserCommandHandler.handle(command);
        return ResponseEntity.noContent().build();
    }
}