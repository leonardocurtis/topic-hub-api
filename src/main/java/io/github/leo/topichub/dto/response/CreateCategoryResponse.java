package io.github.leo.topichub.dto.response;

import java.time.Instant;

public record CreateCategoryResponse(String id, String name, Instant createdAt, boolean isActive) {}
