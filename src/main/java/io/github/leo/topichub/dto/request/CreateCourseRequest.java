package io.github.leo.topichub.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CreateCourseRequest(
        @NotBlank String name, @NotEmpty List<String> categoryIds) {}
