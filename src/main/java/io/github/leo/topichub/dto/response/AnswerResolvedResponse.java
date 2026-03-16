package io.github.leo.topichub.dto.response;

import io.github.leo.topichub.domain.valueobject.TopicStatus;
import io.github.leo.topichub.domain.valueobject.TopicType;

import java.time.Instant;

public record AnswerResolvedResponse(
        String id,
        String title,
        TopicType type,
        String message,
        Instant createdAt,
        TopicStatus status,
        String solvedResponseId,
        String courseId) {}
