package io.github.leo.topichub.dto.response;

import io.github.leo.topichub.domain.valueobject.AccountStatus;
import io.github.leo.topichub.domain.valueobject.Role;

import java.time.Instant;
import java.util.List;

public record UserDetailsResponse(
        String id,
        String name,
        String email,
        List<Role> roles,
        Instant createdAt,
        String deactivatedBy,
        Instant deactivatedAt,
        Instant updatedAt,
        AccountStatus status,
        Instant reactivatedAt,
        Instant suspendedAt,
        String suspendedBy) {}
