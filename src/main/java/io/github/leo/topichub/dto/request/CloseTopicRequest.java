package io.github.leo.topichub.dto.request;

import io.github.leo.topichub.domain.valueobject.TopicCloseReason;
import jakarta.validation.constraints.NotNull;

public record CloseTopicRequest(@NotNull TopicCloseReason reason) {}
