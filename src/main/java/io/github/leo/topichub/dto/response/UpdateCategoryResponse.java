package io.github.leo.topichub.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Response returned after updating a category")
public record UpdateCategoryResponse(String id, String name, Instant createdAt) {}
