package io.github.leo.topichub.dto.response;

import io.github.leo.topichub.domain.valueobject.Role;

public record ChangeRoleResponse(String id, String name, Role role) {}
