package br.com.ferreiradev.fmu.core.presentation.dto;

import br.com.ferreiradev.fmu.core.domain.model.Fish;
import br.com.ferreiradev.fmu.core.domain.model.enums.FishType;

public record FishRecord(String name, FishType type) {
    public FishRecord(Fish fish) {
        this(fish.getName(), fish.getType());
    }
}
