package io.github.leo.topichub.dto.response;

import io.github.leo.topichub.domain.valueobject.TopicType;

import java.time.Instant;

public record UpdateTopicResponse(
        String id, String title, TopicType type, String message, Instant createdAt, String authorId, String courseId) {}
