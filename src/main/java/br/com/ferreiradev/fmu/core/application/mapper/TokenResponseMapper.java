package br.com.ferreiradev.fmu.core.application.mapper;

import br.com.ferreiradev.fmu.core.domain.model.TokenResponse;
import br.com.ferreiradev.fmu.core.presentation.dto.TokenRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TokenResponseMapper {

    TokenRecord toRecord(TokenResponse object);
}
