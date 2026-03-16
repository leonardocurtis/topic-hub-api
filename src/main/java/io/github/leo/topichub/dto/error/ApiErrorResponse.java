package io.github.leo.topichub.dto.error;

import java.util.List;

public record ApiErrorResponse(Integer status, String message, List<ApiErrorValidation> fields) {}
