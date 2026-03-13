package io.github.leo.topichub.controller;

import io.github.leo.topichub.dto.request.CreateUserRequest;
import io.github.leo.topichub.dto.request.LoginRequest;
import io.github.leo.topichub.dto.response.CreateUserResponse;
import io.github.leo.topichub.dto.response.LoginResponse;
import io.github.leo.topichub.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Authentication and authorization endpoints")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest dto) {

        var user = userService.registerUser(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/login")
    @Operation(
            summary = "Authenticate user",
            description =
                    "Authenticates a user using username and password credentials. Returns a JWT token that must be"
                            + " used in the Authorization header as a Bearer token to access protected endpoints.")
    public ResponseEntity<LoginResponse> signIn(@RequestBody @Valid LoginRequest dto) {

        var response = userService.login(dto);

        return ResponseEntity.ok(response);
    }
}
