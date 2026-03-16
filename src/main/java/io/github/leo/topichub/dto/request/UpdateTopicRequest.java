package io.github.leo.topichub.dto.request;

import io.github.leo.topichub.domain.valueobject.TopicType;
import jakarta.validation.constraints.Size;

public record UpdateTopicRequest(
        @Size(min = 1, message = "Title must not be empty if provided")
        String title,

        TopicType type,

        @Size(min = 1, message = "Message must not be empty if provided")
        String message) {}
