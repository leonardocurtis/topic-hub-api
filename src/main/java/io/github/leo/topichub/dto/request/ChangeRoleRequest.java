package io.github.leo.topichub.dto.request;

import io.github.leo.topichub.domain.valueobject.Role;
import jakarta.validation.constraints.NotBlank;

public record ChangeRoleRequest(@NotBlank Role role) {}
