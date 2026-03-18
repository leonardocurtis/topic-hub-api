package io.github.leo.topichub.dto.response;

import io.github.leo.topichub.domain.valueobject.TopicCloseReason;
import io.github.leo.topichub.domain.valueobject.TopicStatus;
import io.github.leo.topichub.domain.valueobject.TopicType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Response returned after updating a topic")
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
