package br.com.ferreiradev.fmu.core.application.service;

import br.com.ferreiradev.fmu.core.presentation.dto.TokenRecord;

public interface AuthUserService {

    TokenRecord generateJWT(String sessionId);

    TokenRecord refreshJWT(String sessionId);
}
