package br.com.ferreiradev.fmu.core.presentation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.List;

import static br.com.ferreiradev.fmu.core.domain.constants.ErrorConstants.INVALID_EMAIL_MESSAGE;
import static br.com.ferreiradev.fmu.core.domain.constants.ErrorConstants.REQUIRED_FIELD_MESSAGE;

@Builder
public record UserRecord(@NotBlank(message = REQUIRED_FIELD_MESSAGE) String username,
                         @NotBlank(message = REQUIRED_FIELD_MESSAGE) String password,
                         @NotBlank(message = REQUIRED_FIELD_MESSAGE) @Email(message = INVALID_EMAIL_MESSAGE) String email,
                         List<String> roles) {
}
