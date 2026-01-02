package br.com.ferreiradev.fmu.core.presentation.dto;

public record TokenRecord(
        String accessToken,
        String scope,
        String tokenType,
        int expiresIn) {
}
