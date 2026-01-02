package br.com.ferreiradev.fmu.core.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import static br.com.ferreiradev.fmu.core.domain.constants.ErrorConstants.REQUIRED_FIELD_MESSAGE;

@Builder
public record ClientRecord(@NotBlank(message = REQUIRED_FIELD_MESSAGE) String clientId,
                           @NotBlank(message = REQUIRED_FIELD_MESSAGE) String clientSecret,
                           @NotBlank(message = REQUIRED_FIELD_MESSAGE) String redirectUri,
                           String scope) {
}
