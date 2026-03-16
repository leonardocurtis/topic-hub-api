package io.github.leo.topichub.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UpdateCourseRequest(
        @NotBlank String name,

        @Size(min = 1, message = "Category list must not be empty if provided")
        List<String> categoryIds) {}
