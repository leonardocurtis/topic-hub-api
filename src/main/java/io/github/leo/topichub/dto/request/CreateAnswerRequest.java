package io.github.leo.topichub.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request payload used to create a response")
public record CreateAnswerRequest(@NotBlank String message) {}
