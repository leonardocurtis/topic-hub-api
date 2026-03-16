package io.github.leo.topichub.dto.response;

import java.time.Instant;

public record CategoryDetailsResponse(String id, String name, Instant createdAt, boolean active) {}
