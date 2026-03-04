package io.github.leo.topichub.dto.response;

import io.github.leo.topichub.domain.valueobject.TopicStatus;
import io.github.leo.topichub.domain.valueobject.TopicType;

import java.time.LocalDateTime;

public record CreateTopicResponse(
        String id,
        String title,
        TopicType type,
        String message,
        LocalDateTime createdAt,
        TopicStatus status,
        String authorId,
        String courseId
) {
}
