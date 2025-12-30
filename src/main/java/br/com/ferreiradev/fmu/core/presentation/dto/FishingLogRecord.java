package br.com.ferreiradev.fmu.core.presentation.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record FishingLogRecord(Long id, LocalDate date, String place, Double weight, FishRecord fish) {
}
