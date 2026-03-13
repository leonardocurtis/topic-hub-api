package io.github.leo.topichub.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record UpdateCourseResponse(String id, String name, LocalDateTime createdAt, List<String> categories) {}
