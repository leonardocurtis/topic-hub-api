package io.github.leo.topichub.dto.response;

import io.github.leo.topichub.domain.valueobject.TopicStatus;
import io.github.leo.topichub.domain.valueobject.TopicType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Response returned when an answer is marked as the accepted solution")
public record AnswerResolvedResponse(
        String id,
        String title,
        TopicType type,
        String message,
        Instant createdAt,
        TopicStatus status,
        String solvedResponseId,
        String courseId) {}
