package io.github.leo.topichub.dto.response;

import io.github.leo.topichub.domain.valueobject.TopicStatus;
import io.github.leo.topichub.domain.valueobject.TopicType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Response returned after creating a topic")
public record CreateTopicResponse(
        String id,
        String title,
        TopicType type,
        String message,
        Instant createdAt,
        TopicStatus status,
        String authorId,
        String courseId) {}
