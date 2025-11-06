package br.com.ferreiradev.fmu.core.presentation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

import static br.com.ferreiradev.fmu.core.infrastructure.adapter.rest.exception.constants.ErrorConstants.INVALID_EMAIL_MESSAGE;
import static br.com.ferreiradev.fmu.core.infrastructure.adapter.rest.exception.constants.ErrorConstants.REQUIRED_FIELD_MESSAGE;

public record UserRecord(@NotBlank(message = "'username'" + REQUIRED_FIELD_MESSAGE) String username,
                         @NotBlank(message = "'password'" + REQUIRED_FIELD_MESSAGE) String password,
                         @NotBlank(message = "'email'" +  REQUIRED_FIELD_MESSAGE) @Email(message = INVALID_EMAIL_MESSAGE) String email,
                         List<String> roles) {
}
