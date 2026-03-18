package io.github.leo.topichub.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request payload used to update a category")
public record UpdateCategoryRequest(@NotBlank String name) {}
