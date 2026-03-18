package io.github.leo.topichub.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Category summary used in category lists")
public record CategoriesListResponse(String id, String name, Instant createdAt) {}
