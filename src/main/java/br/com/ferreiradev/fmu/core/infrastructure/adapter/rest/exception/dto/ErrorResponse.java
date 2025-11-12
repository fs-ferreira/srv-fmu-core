package br.com.ferreiradev.fmu.core.infrastructure.adapter.rest.exception.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Default server error response")
public record ErrorResponse(
        @Schema(description = "Error timestamp", example = "2025-09-11T19:40:00")
        LocalDateTime timestamp,

        @Schema(description = "HTTP Status Code", example = "500")
        int status,

        @Schema(description = "Friendly error message", example = "Validation failed")
        String message,

        @Schema(description = "List of validation errors")
        List<FieldValidationError> errors
) {}

