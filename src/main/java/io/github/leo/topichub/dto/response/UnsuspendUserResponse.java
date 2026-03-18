package io.github.leo.topichub.dto.response;

import io.github.leo.topichub.domain.valueobject.AccountStatus;
import io.github.leo.topichub.domain.valueobject.Role;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Response returned after reactivating a suspended user")
public record UnsuspendUserResponse(
        String id, String name, String email, Role role, AccountStatus status, Instant createdAt) {}
