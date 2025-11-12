package br.com.ferreiradev.fmu.core.application.service.impl;

import br.com.ferreiradev.fmu.core.application.mapper.ClientMapper;
import br.com.ferreiradev.fmu.core.application.service.AuthClientService;
import br.com.ferreiradev.fmu.core.domain.model.auth.AuthClient;
import br.com.ferreiradev.fmu.core.domain.repository.AuthClientRepository;
import br.com.ferreiradev.fmu.core.infrastructure.adapter.rest.exception.ResourceNotFoundException;
import br.com.ferreiradev.fmu.core.presentation.dto.ClientRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static br.com.ferreiradev.fmu.core.infrastructure.adapter.rest.exception.constants.ErrorConstants.NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
public class AuthClientServiceImpl implements AuthClientService {

    private final AuthClientRepository repository;
    private final PasswordEncoder encoder;
    private final ClientMapper mapper;

    @Override
    public ClientRecord create(ClientRecord client){
        AuthClient entity = mapper.toEntity(client);
        entity.setClientSecret(encoder.encode(client.clientSecret()));
        return mapper.toRecord(repository.save(entity));
    }

    @Override
    public AuthClient findByClientId(String clientId){
        return repository.findByClientId(clientId).orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MESSAGE));
    }
}
