package io.github.leo.topichub.dto.response;

import java.time.Instant;

public record UpdateCategoryResponse(String id, String name, Instant createdAt) {}
