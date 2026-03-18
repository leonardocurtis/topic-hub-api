package io.github.leo.topichub.dto.request;

import io.github.leo.topichub.domain.validation.StrongPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request payload used to register a new user")
public record CreateUserRequest(
        @Schema(example = "John Doe") @NotBlank String name,
        @Schema(example = "john.doe@email.com") @NotBlank String email,

        @Schema(example = "P@ssw0rd123") @NotBlank @StrongPassword
        String password) {}
