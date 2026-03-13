package io.github.leo.topichub.dto.response;

import java.time.Instant;

public record UpdateUserProfileResponse(String name, String email, Instant createdAt, Instant updatedAt) {}
