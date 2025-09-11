package br.com.ferreiradev.fmu.core.application.mapper;

import br.com.ferreiradev.fmu.core.domain.model.Fish;
import br.com.ferreiradev.fmu.core.presentation.dto.FishRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FishMapper {

    FishRecord toRecord(Fish fish);
    Fish toEntity(FishRecord record);
}
