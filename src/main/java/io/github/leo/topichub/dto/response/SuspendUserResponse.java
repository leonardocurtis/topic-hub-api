package io.github.leo.topichub.dto.response;

import io.github.leo.topichub.domain.valueobject.AccountStatus;
import io.github.leo.topichub.domain.valueobject.Role;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Response returned after a user account is successfully suspended")
public record SuspendUserResponse(
        @Schema(description = "Unique identifier of the suspended user")
        String id,

        @Schema(description = "Full name of the suspended user", example = "John Doe")
        String name,

        @Schema(description = "Email address of the suspended user", example = "john.doe@example.com")
        String email,

        @Schema(description = "Role assigned to the user, determining their permission level", example = "USER")
        Role role,

        @Schema(
                description = "Current status of the user account — expected to be SUSPENDED after this operation",
                example = "SUSPENDED")
        AccountStatus status,

        @Schema(
                description = "Timestamp of when the user account was originally created (UTC)",
                example = "2024-01-15T10:30:00Z")
        Instant createdAt,

        @Schema(
                description = "Timestamp of when the account suspension was applied (UTC)",
                example = "2024-07-20T14:45:00Z")
        Instant suspendedAt,

        @Schema(description = "ID of the user who performed the suspension")
        String suspendedBy) {}
