package io.github.leo.topichub.dto.response;

import io.github.leo.topichub.domain.valueobject.AccountStatus;
import io.github.leo.topichub.domain.valueobject.Role;

import java.time.Instant;

public record UserDetailsResponse(
        String id,
        String name,
        String email,
        Role role,
        Instant createdAt,
        String deactivatedBy,
        Instant deactivatedAt,
        Instant updatedAt,
        AccountStatus status,
        Instant reactivatedAt,
        Instant suspendedAt,
        String suspendedBy) {}
