package io.github.leo.topichub.dto.response;

import io.github.leo.topichub.domain.valueobject.Role;

import java.util.List;

public record ChangeRoleResponse(String id, String name, List<Role> roles) {}
