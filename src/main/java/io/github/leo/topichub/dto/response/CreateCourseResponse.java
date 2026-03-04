package io.github.leo.topichub.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record CreateCourseResponse(String id, String name, LocalDateTime createdAt, List<String> categories) {}
