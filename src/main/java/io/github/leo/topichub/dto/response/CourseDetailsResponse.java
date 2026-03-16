package io.github.leo.topichub.dto.response;

import java.time.Instant;
import java.util.List;

public record CourseDetailsResponse(
        String id, String name, Instant createdAt, boolean active, List<String> categories) {}
