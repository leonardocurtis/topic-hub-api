package io.github.leo.topichub.dto.request;

import io.github.leo.topichub.domain.valueobject.TopicType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Request payload used to create a new topic")
public record CreateTopicRequest(
        @NotBlank String title,
        @NotNull TopicType type,
        @NotBlank @Size(min = 2, max = 5000) String message,
        @NotBlank String course) {}
