package io.github.leo.topichub.dto.response;

import io.github.leo.topichub.domain.valueobject.Role;

import java.time.Instant;

public record UserProfileResponse(String name, String email, Role role, Instant createdAt) {}
