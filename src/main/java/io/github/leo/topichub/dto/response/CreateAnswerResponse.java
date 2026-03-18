package io.github.leo.topichub.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response returned after creating a response")
public record CreateAnswerResponse(String id, String message, String authorId, String topicId) {}
