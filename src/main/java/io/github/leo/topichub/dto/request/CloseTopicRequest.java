package io.github.leo.topichub.dto.request;

import io.github.leo.topichub.domain.valueobject.TopicCloseReason;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request payload used to close a topic")
public record CloseTopicRequest(@NotNull TopicCloseReason reason) {}
