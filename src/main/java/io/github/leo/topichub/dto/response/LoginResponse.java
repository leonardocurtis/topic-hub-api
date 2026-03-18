package io.github.leo.topichub.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response returned after successful authentication")
public record LoginResponse(
        @Schema(description = "JWT authentication token") String accessToken) {}
