package br.com.ferreiradev.fmu.core.presentation.dto;

import jakarta.validation.constraints.NotBlank;

import static br.com.ferreiradev.fmu.core.infrastructure.adapter.rest.exception.constants.ErrorConstants.REQUIRED_FIELD_MESSAGE;

public record ClientRecord(@NotBlank(message = REQUIRED_FIELD_MESSAGE) String clientId,
                           @NotBlank(message = REQUIRED_FIELD_MESSAGE) String clientSecret,
                           @NotBlank(message = REQUIRED_FIELD_MESSAGE) String redirectUri,
                           String scope) {
}
