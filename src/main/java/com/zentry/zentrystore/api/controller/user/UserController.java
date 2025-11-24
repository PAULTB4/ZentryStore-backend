package com.zentry.zentrystore.api.controller.user;

import com.zentry.zentrystore.application.user.command.DeleteUserCommand;
import com.zentry.zentrystore.application.user.command.DeleteUserCommandHandler;
import com.zentry.zentrystore.application.user.dto.UserDTO;
import com.zentry.zentrystore.application.user.dto.response.UserResponse;
import com.zentry.zentrystore.application.user.query.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final GetUserByIdQueryHandler getUserByIdQueryHandler;
    private final GetUserByEmailQueryHandler getUserByEmailQueryHandler;
    private final GetUserByUsernameQueryHandler getUserByUsernameQueryHandler;
    private final GetActiveUsersQueryHandler getActiveUsersQueryHandler;
    private final SearchUsersQueryHandler searchUsersQueryHandler;
    private final DeleteUserCommandHandler deleteUserCommandHandler;

    public UserController(
            GetUserByIdQueryHandler getUserByIdQueryHandler,
            GetUserByEmailQueryHandler getUserByEmailQueryHandler,
            GetUserByUsernameQueryHandler getUserByUsernameQueryHandler,
            GetActiveUsersQueryHandler getActiveUsersQueryHandler,
            SearchUsersQueryHandler searchUsersQueryHandler,
            DeleteUserCommandHandler deleteUserCommandHandler) {
        this.getUserByIdQueryHandler = getUserByIdQueryHandler;
        this.getUserByEmailQueryHandler = getUserByEmailQueryHandler;
        this.getUserByUsernameQueryHandler = getUserByUsernameQueryHandler;
        this.getActiveUsersQueryHandler = getActiveUsersQueryHandler;
        this.searchUsersQueryHandler = searchUsersQueryHandler;
        this.deleteUserCommandHandler = deleteUserCommandHandler;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        GetUserByIdQuery query = new GetUserByIdQuery(id);
        UserResponse response = getUserByIdQueryHandler.handle(query);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        GetUserByEmailQuery query = new GetUserByEmailQuery(email);
        UserDTO response = getUserByEmailQueryHandler.handle(query);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        GetUserByUsernameQuery query = new GetUserByUsernameQuery(username);
        UserDTO response = getUserByUsernameQueryHandler.handle(query);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/active")
    public ResponseEntity<List<UserDTO>> getActiveUsers() {
        GetActiveUsersQuery query = new GetActiveUsersQuery();
        List<UserDTO> response = getActiveUsersQueryHandler.handle(query);
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