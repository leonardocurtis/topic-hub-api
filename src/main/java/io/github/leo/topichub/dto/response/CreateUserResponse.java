package io.github.leo.topichub.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response returned after successful user registration")
public record CreateUserResponse(String id, String name, String email) {}
