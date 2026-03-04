package io.github.leo.topichub.dto.request;

import io.github.leo.topichub.domain.valueobject.ModerationReason;
import jakarta.validation.constraints.NotNull;

public record DeactivateResponseRequest(@NotNull ModerationReason reason) {}
