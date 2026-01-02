package br.com.ferreiradev.fmu.core.presentation.dto;

import jakarta.validation.constraints.NotBlank;

import static br.com.ferreiradev.fmu.core.domain.constants.ErrorConstants.REQUIRED_FIELD_MESSAGE;

public record AuthRecord(@NotBlank(message = REQUIRED_FIELD_MESSAGE) String username,
                         @NotBlank(message = REQUIRED_FIELD_MESSAGE) String password) {
}
