package io.github.leo.topichub.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Schema(description = "Request payload used to update a course")
public record UpdateCourseRequest(
        @NotBlank String name,

        @Size(min = 1, message = "Category list must not be empty if provided")
        List<String> categoryIds) {}
