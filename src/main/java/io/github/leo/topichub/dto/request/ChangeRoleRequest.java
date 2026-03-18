package io.github.leo.topichub.dto.request;

import io.github.leo.topichub.domain.valueobject.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request payload to change a user's role")
public record ChangeRoleRequest(
        @Schema(description = "New role assigned to the user", example = "MODERATOR") @NotNull
        Role role) {}
