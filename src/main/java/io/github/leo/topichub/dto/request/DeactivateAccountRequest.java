package io.github.leo.topichub.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request payload to deactivate user account")
public record DeactivateAccountRequest(String password) {}
