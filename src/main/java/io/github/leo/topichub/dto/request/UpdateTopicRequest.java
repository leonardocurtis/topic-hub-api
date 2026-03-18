package io.github.leo.topichub.dto.request;

import io.github.leo.topichub.domain.valueobject.TopicType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(description = "Request payload used to update a topic")
public record UpdateTopicRequest(
        @Size(min = 1, message = "Title must not be empty if provided")
        String title,

        TopicType type,

        @Size(min = 1, message = "Message must not be empty if provided")
        String message) {}
