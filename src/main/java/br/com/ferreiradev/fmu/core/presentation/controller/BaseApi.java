package br.com.ferreiradev.fmu.core.presentation.controller;

import br.com.ferreiradev.fmu.core.infrastructure.adapter.rest.exception.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@ApiResponse(
        responseCode = "500",
        description = "Unexpected server error",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class)
        )
)
public interface BaseApi {
}
