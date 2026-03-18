package io.github.leo.topichub.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.List;

@Schema(description = "Course summary used in course lists")
public record CoursesListResponse(
        @Schema(description = "Unique identifier of the newly created course")
        String id,

        @Schema(description = "Name of the created course") String name,

        @Schema(description = "Timestamp of when the course was created (UTC)")
        Instant createdAt,

        @Schema(description = "List of category names associated with the course")
        List<String> categoryIds) {}
