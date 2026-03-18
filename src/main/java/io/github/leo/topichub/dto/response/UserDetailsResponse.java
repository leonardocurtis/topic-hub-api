package io.github.leo.topichub.dto.response;

import io.github.leo.topichub.domain.valueobject.AccountStatus;
import io.github.leo.topichub.domain.valueobject.Role;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Detailed information about a user account")
public record UserDetailsResponse(
        @Schema(description = "Unique identifier of the user")
        String id,

        @Schema(description = "Full name of the user", example = "John Doe")
        String name,

        @Schema(description = "Email address of the user", example = "john.doe@example.com")
        String email,

        @Schema(description = "Role assigned to the user, determining their permission level", example = "ADMIN")
        Role role,

        @Schema(description = "Timestamp of when the user account was created (UTC)", example = "2024-01-15T10:30:00Z")
        Instant createdAt,

        @Schema(
                description = "ID of the user who last deactivated this account. Null if never deactivated",
                example = "admin-user-id-123",
                nullable = true)
        String lastDeactivatedBy,

        @Schema(
                description = "Timestamp of the most recent account deactivation (UTC). Null if never deactivated",
                example = "2024-06-01T08:00:00Z",
                nullable = true)
        Instant lastDeactivatedAt,

        @Schema(
                description = "Timestamp of the last update made to this account (UTC)",
                example = "2024-07-20T14:45:00Z")
        Instant updatedAt,

        @Schema(description = "Current status of the user account", example = "ACTIVE")
        AccountStatus status,

        @Schema(
                description = "Timestamp of the most recent account reactivation (UTC). Null if never reactivated",
                example = "2024-07-01T09:00:00Z",
                nullable = true)
        Instant lastReactivatedAt,

        @Schema(
                description = "Timestamp of the most recent account suspension (UTC). Null if never suspended",
                example = "2024-05-10T16:20:00Z",
                nullable = true)
        Instant lastSuspendedAt,

        @Schema(
                description = "ID of the user who last suspended this account. Null if never suspended",
                example = "admin-user-id-456",
                nullable = true)
        String lastSuspendedBy) {}
