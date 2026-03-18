package io.github.leo.topichub.dto.response;

import io.github.leo.topichub.domain.valueobject.TopicType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Response returned after closing a topic")
public record UpdateTopicResponse(
        String id, String title, TopicType type, String message, Instant createdAt, String authorId, String courseId) {}
