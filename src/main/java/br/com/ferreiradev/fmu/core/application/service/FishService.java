package br.com.ferreiradev.fmu.core.application.service;

import br.com.ferreiradev.fmu.core.presentation.dto.FishRecord;

import java.util.List;

public interface FishService {

    List<FishRecord> findAll();

    FishRecord create(FishRecord fishRecord);
}
