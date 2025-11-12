package br.com.ferreiradev.fmu.core.application.mapper;

import br.com.ferreiradev.fmu.core.domain.model.auth.AuthClient;
import br.com.ferreiradev.fmu.core.presentation.dto.ClientRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientRecord toRecord(AuthClient log);
    AuthClient toEntity(ClientRecord record);
}
