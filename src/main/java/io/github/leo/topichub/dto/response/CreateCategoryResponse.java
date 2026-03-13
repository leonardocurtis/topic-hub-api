package io.github.leo.topichub.dto.response;

import java.time.LocalDateTime;

public record CreateCategoryResponse(String id, String name, LocalDateTime createdAt, boolean isActive) {}
