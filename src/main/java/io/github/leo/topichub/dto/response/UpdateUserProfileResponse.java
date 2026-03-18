package io.github.leo.topichub.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Response returned after updating user profile")
public record UpdateUserProfileResponse(String name, String email, Instant createdAt, Instant updatedAt) {}
