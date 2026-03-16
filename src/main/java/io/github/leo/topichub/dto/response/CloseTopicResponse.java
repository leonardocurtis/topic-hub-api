package io.github.leo.topichub.dto.response;

import io.github.leo.topichub.domain.valueobject.TopicCloseReason;
import io.github.leo.topichub.domain.valueobject.TopicStatus;
import io.github.leo.topichub.domain.valueobject.TopicType;

import java.time.Instant;

public record CloseTopicResponse(
        String id,
        String title,
        TopicType type,
        String message,
        Instant createdAt,
        TopicStatus status,
        String authorId,
        String courseId,
        String lastSolvedResponseId,
        TopicCloseReason closedReason,
        String closedBy,
        Instant closedAt) {}
