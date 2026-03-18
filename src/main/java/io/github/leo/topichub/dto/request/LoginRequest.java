package io.github.leo.topichub.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request payload used for user authentication")
public record LoginRequest(
        @Schema(example = "john.doe@email.com") @NotBlank String email,
        @Schema(example = "P@ssw0rd123") @NotBlank String password) {}
