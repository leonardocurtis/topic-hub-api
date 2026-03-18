package io.github.leo.topichub.controller;

import io.github.leo.topichub.dto.request.CreateUserRequest;
import io.github.leo.topichub.dto.request.LoginRequest;
import io.github.leo.topichub.dto.response.CreateUserResponse;
import io.github.leo.topichub.dto.response.LoginResponse;
import io.github.leo.topichub.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Tag(name = "Authentication", description = "Endpoints responsible for user registration and authentication.")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Register a new user", description = """
        Creates a new user account.

        Requirements:

        1 - The **username** must be unique

        2 - The **email** must be unique

        3 - The **password** must meet the validation rules

        This endpoint **does not require authentication**.
        """)
    @ApiResponses({
        @ApiResponse(
                responseCode = "201",
                description = "User registered successfully",
                content =
                        @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = CreateUserResponse.class)))
    })
    @PostMapping("/register")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest dto) {

        var user = userService.registerUser(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @Operation(summary = "Authenticate user", description = """
        Authenticates a user using **email** and **password**.

        If the credentials are valid, the API returns a **JWT token**.

        The token must be included in future requests using the header:

        `Authorization: Bearer <token>`

        Token expiration: **1 hour**.
        """)
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Authentication successful",
                content =
                        @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = LoginResponse.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> signIn(@RequestBody @Valid LoginRequest dto) {

        var response = userService.login(dto);

        return ResponseEntity.ok(response);
    }
}
