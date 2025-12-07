package com.zentry.zentrystore.api.controller.user;

import com.zentry.zentrystore.application.user.command.*;
import com.zentry.zentrystore.application.user.dto.UserDTO;
import com.zentry.zentrystore.application.user.dto.request.ChangePasswordRequest;
import com.zentry.zentrystore.application.user.dto.response.UserResponse;
import com.zentry.zentrystore.application.user.dto.response.UserStatisticsResponse;
import com.zentry.zentrystore.application.user.query.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final GetUserStatisticsQueryHandler getUserStatisticsQueryHandler;
    private final DeleteUserCommandHandler deleteUserCommandHandler;
    private final ChangePasswordCommandHandler changePasswordCommandHandler;
    private final VerifyEmailCommandHandler verifyEmailCommandHandler;

    public UserController(
            GetUserByIdQueryHandler getUserByIdQueryHandler,
            GetUserByEmailQueryHandler getUserByEmailQueryHandler,
            GetUserByUsernameQueryHandler getUserByUsernameQueryHandler,
            GetActiveUsersQueryHandler getActiveUsersQueryHandler,
            SearchUsersQueryHandler searchUsersQueryHandler,
            GetUserStatisticsQueryHandler getUserStatisticsQueryHandler,
            DeleteUserCommandHandler deleteUserCommandHandler,
            ChangePasswordCommandHandler changePasswordCommandHandler,
            VerifyEmailCommandHandler verifyEmailCommandHandler) {
        this.getUserByIdQueryHandler = getUserByIdQueryHandler;
        this.getUserByEmailQueryHandler = getUserByEmailQueryHandler;
        this.getUserByUsernameQueryHandler = getUserByUsernameQueryHandler;
        this.getActiveUsersQueryHandler = getActiveUsersQueryHandler;
        this.searchUsersQueryHandler = searchUsersQueryHandler;
        this.getUserStatisticsQueryHandler = getUserStatisticsQueryHandler;
        this.deleteUserCommandHandler = deleteUserCommandHandler;
        this.changePasswordCommandHandler = changePasswordCommandHandler;
        this.verifyEmailCommandHandler = verifyEmailCommandHandler;
    }

    // =============================================
    // QUERIES (GET)
    // =============================================

    // ✅ Cualquier usuario autenticado puede ver perfiles
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        GetUserByIdQuery query = new GetUserByIdQuery(id);
        UserResponse response = getUserByIdQueryHandler.handle(query);
        return ResponseEntity.ok(response);
    }

    // ✅ Solo ADMIN puede buscar por email
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        GetUserByEmailQuery query = new GetUserByEmailQuery(email);
        UserDTO response = getUserByEmailQueryHandler.handle(query);
        return ResponseEntity.ok(response);
    }

    // ✅ Cualquier usuario autenticado
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        GetUserByUsernameQuery query = new GetUserByUsernameQuery(username);
        UserDTO response = getUserByUsernameQueryHandler.handle(query);
        return ResponseEntity.ok(response);
    }

    // ✅ Solo ADMIN puede ver todos los usuarios activos
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/active")
    public ResponseEntity<List<UserDTO>> getActiveUsers() {
        GetActiveUsersQuery query = new GetActiveUsersQuery();
        List<UserDTO> response = getActiveUsersQueryHandler.handle(query);
        return ResponseEntity.ok(response);
    }

    // ✅ Cualquier usuario autenticado puede buscar
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/search")
    public ResponseEntity<List<UserResponse>> searchUsers(@RequestParam String keyword) {
        SearchUsersQuery query = new SearchUsersQuery(keyword);
        List<UserResponse> response = searchUsersQueryHandler.handle(query);
        return ResponseEntity.ok(response);
    }

    // ✅ Cualquier usuario autenticado puede ver estadísticas
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/statistics")
    public ResponseEntity<UserStatisticsResponse> getUserStatistics(@PathVariable Long id) {
        GetUserStatisticsQuery query = new GetUserStatisticsQuery(id);
        UserStatisticsResponse response = getUserStatisticsQueryHandler.handle(query);
        return ResponseEntity.ok(response);
    }

    // =============================================
    // COMMANDS (POST, PUT, PATCH, DELETE)
    // =============================================

    // ✅ Solo usuario autenticado (validación de ownership en Handler)
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}/password")
    public ResponseEntity<Void> changePassword(
            @PathVariable Long id,
            @Valid @RequestBody ChangePasswordRequest request) {
        ChangePasswordCommand command = new ChangePasswordCommand(
                id,
                request.getCurrentPassword(),
                request.getNewPassword()
        );
        changePasswordCommandHandler.handle(command);
        return ResponseEntity.ok().build();
    }

    // ✅ Solo ADMIN puede verificar emails manualmente
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}/verify-email")
    public ResponseEntity<Void> verifyEmail(@PathVariable Long id) {
        VerifyEmailCommand command = new VerifyEmailCommand(id);
        verifyEmailCommandHandler.handle(command);
        return ResponseEntity.ok().build();
    }

    // ✅ Solo ADMIN puede eliminar usuarios
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        DeleteUserCommand command = new DeleteUserCommand(id);
        deleteUserCommandHandler.handle(command);
        return ResponseEntity.noContent().build();
    }
}