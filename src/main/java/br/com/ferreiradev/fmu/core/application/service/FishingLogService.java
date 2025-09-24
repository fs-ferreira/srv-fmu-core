package br.com.ferreiradev.fmu.core.application.service;

import br.com.ferreiradev.fmu.core.presentation.dto.FishingLogRecord;

import java.util.List;

public interface FishingLogService {
    FishingLogRecord create(FishingLogRecord record);
    List<FishingLogRecord> findAll();
    void validateLog(Long id);
}
