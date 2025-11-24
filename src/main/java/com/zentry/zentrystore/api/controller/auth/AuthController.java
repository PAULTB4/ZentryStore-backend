package com.zentry.zentrystore.api.controller.auth;

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

    public AuthController(RegisterUserCommandHandler registerUserCommandHandler) {
        this.registerUserCommandHandler = registerUserCommandHandler;
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
        // TODO: Implementar con JWT
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.noContent().build();
    }
}