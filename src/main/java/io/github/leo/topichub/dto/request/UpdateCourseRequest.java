package io.github.leo.topichub.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UpdateCourseRequest(
        @NotBlank String name, @NotNull List<String> categoryIds) {}
