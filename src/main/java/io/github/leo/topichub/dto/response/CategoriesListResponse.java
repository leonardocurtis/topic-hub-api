package io.github.leo.topichub.dto.response;

import java.time.Instant;

public record CategoriesListResponse(String id, String name, Instant createdAt) {}
