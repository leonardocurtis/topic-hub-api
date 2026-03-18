package io.github.leo.topichub.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Schema(description = "Request payload used to create a new course")
public record CreateCourseRequest(
        @NotBlank @Schema(description = "Course name", example = "Java Spring Boot")
        String name,

        @NotEmpty
        @Schema(description = "List of category IDs to associate with the course. Must contain at least one entry")
        List<String> categoryIds) {}
