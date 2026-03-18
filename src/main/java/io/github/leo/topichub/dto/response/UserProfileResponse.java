package io.github.leo.topichub.dto.response;

import io.github.leo.topichub.domain.valueobject.Role;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Authenticated user's profile information")
public record UserProfileResponse(String name, String email, Role role, Instant createdAt) {}
