package io.github.leo.topichub.dto.response;

import io.github.leo.topichub.domain.valueobject.TopicStatus;
import io.github.leo.topichub.domain.valueobject.TopicType;

import java.time.LocalDateTime;

public record TopicListResponse(
        String id,
        String title,
        String message,
        TopicType topicType,
        LocalDateTime createdAt,
        TopicStatus status,
        String author,
        String course,
        boolean isActive) {}
