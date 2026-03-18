package io.github.leo.topichub.dto.response;

import io.github.leo.topichub.domain.valueobject.Role;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response returned after a user's role has been updated")
public record ChangeRoleResponse(String id, String name, Role role) {}
