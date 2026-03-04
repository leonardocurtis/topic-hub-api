package io.github.leo.topichub.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Authentication response containing JWT token")
public record LoginResponse(
        @Schema(description = "JWT access token. Must be sent in the Authorization header as Bearer token")
        String accessToken) {}
