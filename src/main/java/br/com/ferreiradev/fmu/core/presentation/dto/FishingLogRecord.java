package br.com.ferreiradev.fmu.core.presentation.dto;

import java.time.LocalDate;

public record FishingLogRecord(Long id, LocalDate date, String place, Double weight, FishRecord fish) {
}
