package io.github.leo.topichub.dto.response;

import io.github.leo.topichub.domain.valueobject.TopicStatus;
import io.github.leo.topichub.domain.valueobject.TopicType;

import java.time.Instant;

public record TopicDetailsResponse(
        String id,
        String title,
        String message,
        TopicType topicType,
        Instant createdAt,
        TopicStatus status,
        String author,
        String course) {}
