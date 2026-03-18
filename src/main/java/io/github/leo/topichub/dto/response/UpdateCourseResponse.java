package io.github.leo.topichub.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.List;

@Schema(description = "Response returned after updating a course")
public record UpdateCourseResponse(String id, String name, Instant createdAt, List<String> categories) {}
