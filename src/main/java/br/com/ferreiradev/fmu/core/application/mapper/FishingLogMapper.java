package br.com.ferreiradev.fmu.core.application.mapper;

import br.com.ferreiradev.fmu.core.domain.model.FishingLog;
import br.com.ferreiradev.fmu.core.presentation.dto.FishingLogRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FishingLogMapper {

    FishingLogRecord toRecord(FishingLog log);
    FishingLog toEntity(FishingLogRecord record);
}
