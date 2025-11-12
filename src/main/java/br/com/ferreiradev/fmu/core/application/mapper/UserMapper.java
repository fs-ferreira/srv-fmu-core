package br.com.ferreiradev.fmu.core.application.mapper;

import br.com.ferreiradev.fmu.core.domain.model.auth.User;
import br.com.ferreiradev.fmu.core.presentation.dto.UserRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserRecord toRecord(User user);
    User toEntity(UserRecord record);
}
