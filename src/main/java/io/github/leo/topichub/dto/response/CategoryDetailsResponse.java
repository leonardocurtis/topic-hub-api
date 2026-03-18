package io.github.leo.topichub.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Detailed information about a category")
public record CategoryDetailsResponse(String id, String name, Instant createdAt, boolean active) {}
