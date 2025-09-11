package br.com.ferreiradev.fmu.core.presentation.controller;

import br.com.ferreiradev.fmu.core.infrastructure.adapter.rest.exception.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@ApiResponse(
        responseCode = "500",
        description = "Erro inesperado do servidor",
        content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class)
        )
)
public interface BaseControllerApi {
}
