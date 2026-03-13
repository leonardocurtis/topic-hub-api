package io.github.leo.topichub.dto.response;

import io.github.leo.topichub.domain.valueobject.TopicType;

import java.time.LocalDateTime;

public record UpdateTopicResponse(
        String id,
        String title,
        TopicType type,
        String message,
        LocalDateTime createdAt,
        String authorId,
        String courseId
        ) {}
