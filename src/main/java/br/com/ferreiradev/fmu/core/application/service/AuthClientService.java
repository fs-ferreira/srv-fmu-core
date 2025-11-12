package br.com.ferreiradev.fmu.core.application.service;

import br.com.ferreiradev.fmu.core.domain.model.auth.AuthClient;
import br.com.ferreiradev.fmu.core.presentation.dto.ClientRecord;

public interface AuthClientService {

    ClientRecord create(ClientRecord client);
    AuthClient findByClientId(String clientId);
}
