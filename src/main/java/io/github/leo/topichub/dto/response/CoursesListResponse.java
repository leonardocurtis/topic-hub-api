package io.github.leo.topichub.dto.response;

import java.time.Instant;
import java.util.List;

public record CoursesListResponse(String id, String name, Instant createdAt, List<String> categoryIds) {}
