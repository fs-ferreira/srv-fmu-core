package br.com.ferreiradev.fmu.core.application.service;

import br.com.ferreiradev.fmu.core.presentation.dto.UserRecord;

public interface UserService {

    UserRecord create(UserRecord user);
    UserRecord findByUsername(String username);
    UserRecord findByEmail(String email);
}
