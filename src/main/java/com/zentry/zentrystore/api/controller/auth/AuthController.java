package com.zentry.zentrystore.api.controller.auth;

import com.zentry.zentrystore.application.auth.command.LoginCommand;
import com.zentry.zentrystore.application.auth.command.LoginCommandHandler;
import com.zentry.zentrystore.application.user.command.RegisterUserCommand;
import com.zentry.zentrystore.application.user.command.RegisterUserCommandHandler;
import com.zentry.zentrystore.application.user.dto.request.LoginRequest;
import com.zentry.zentrystore.application.user.dto.request.RegisterRequest;
import com.zentry.zentrystore.application.user.dto.response.AuthResponse;
import com.zentry.zentrystore.application.user.dto.response.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final RegisterUserCommandHandler registerUserCommandHandler;
    private final LoginCommandHandler loginCommandHandler;

    public AuthController(RegisterUserCommandHandler registerUserCommandHandler,
                          LoginCommandHandler loginCommandHandler) {
        this.registerUserCommandHandler = registerUserCommandHandler;
        this.loginCommandHandler = loginCommandHandler;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        RegisterUserCommand command = new RegisterUserCommand(
                request.getUsername(),
                request.getEmail(),
                request.getPassword()
        );

        UserResponse response = registerUserCommandHandler.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginCommand command = new LoginCommand(
                request.getEmail(),
                request.getPassword()
        );

        AuthResponse response = loginCommandHandler.handle(command);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        // TODO: Invalidar JWT token
        return ResponseEntity.noContent().build();
    }
}