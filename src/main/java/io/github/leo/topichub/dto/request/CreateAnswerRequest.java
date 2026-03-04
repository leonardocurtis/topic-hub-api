package io.github.leo.topichub.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateAnswerRequest(@NotBlank String message) {}
