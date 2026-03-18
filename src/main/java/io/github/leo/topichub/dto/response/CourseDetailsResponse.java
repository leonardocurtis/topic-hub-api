package io.github.leo.topichub.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.List;

@Schema(description = "Detailed information about a course")
public record CourseDetailsResponse(
        String id,
        String name,
        Instant createdAt,

        @Schema(description = "Indicates whether the course is archived")
        boolean active,

        List<String> categories) {}
