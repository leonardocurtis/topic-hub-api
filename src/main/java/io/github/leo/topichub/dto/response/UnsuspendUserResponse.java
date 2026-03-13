package io.github.leo.topichub.dto.response;

import io.github.leo.topichub.domain.valueobject.AccountStatus;
import io.github.leo.topichub.domain.valueobject.Role;

import java.time.Instant;
import java.util.List;

public record UnsuspendUserResponse(
        String id, String name, String email, List<Role> roles, AccountStatus status, Instant createdAt) {}
