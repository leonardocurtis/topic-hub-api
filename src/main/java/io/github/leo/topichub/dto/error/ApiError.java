package io.github.leo.topichub.dto.error;

import java.time.LocalDateTime;

public record ApiError(Integer status, String error, String message, String path, LocalDateTime timestamp) {}
