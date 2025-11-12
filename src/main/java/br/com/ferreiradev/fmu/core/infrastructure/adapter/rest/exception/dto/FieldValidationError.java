package br.com.ferreiradev.fmu.core.infrastructure.adapter.rest.exception.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Validation error details")
public record FieldValidationError(
        @Schema(description = "Field with validation error", example = "clientId")
        String field,

        @Schema(description = "Validation message", example = "clientId must not be null or empty")
        String error
) {}