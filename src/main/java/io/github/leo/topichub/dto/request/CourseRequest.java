package io.github.leo.topichub.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CourseRequest(@NotBlank String name, @NotBlank String category) {}
