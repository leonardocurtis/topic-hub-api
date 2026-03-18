package io.github.leo.topichub.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Response returned after creating a category")
public record CreateCategoryResponse(String id, String name, Instant createdAt, boolean isActive) {}
