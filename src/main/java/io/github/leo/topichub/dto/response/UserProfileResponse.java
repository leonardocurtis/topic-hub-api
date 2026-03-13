package io.github.leo.topichub.dto.response;

import io.github.leo.topichub.domain.valueobject.Role;

import java.time.Instant;
import java.util.List;

public record UserProfileResponse(String name, String email, List<Role> roles, Instant createdAt) {}
