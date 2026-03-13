package io.github.leo.topichub.dto.request;

import io.github.leo.topichub.domain.valueobject.TopicType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateTopicRequest(
        @NotBlank String title,
        @NotNull TopicType type,
        @NotBlank String message) {}
