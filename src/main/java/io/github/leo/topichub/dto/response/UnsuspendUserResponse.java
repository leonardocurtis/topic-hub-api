package io.github.leo.topichub.dto.response;

import io.github.leo.topichub.domain.valueobject.AccountStatus;
import io.github.leo.topichub.domain.valueobject.Role;

import java.time.Instant;

public record UnsuspendUserResponse(
        String id, String name, String email, Role role, AccountStatus status, Instant createdAt) {}
