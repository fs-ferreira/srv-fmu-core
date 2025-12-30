package br.com.ferreiradev.fmu.core.application.service.impl;


import br.com.ferreiradev.fmu.core.application.mapper.ClientMapper;
import br.com.ferreiradev.fmu.core.domain.model.auth.AuthClient;
import br.com.ferreiradev.fmu.core.domain.repository.AuthClientRepository;
import br.com.ferreiradev.fmu.core.infrastructure.adapter.rest.exception.ResourceNotFoundException;
import br.com.ferreiradev.fmu.core.presentation.dto.ClientRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static br.com.ferreiradev.fmu.core.infrastructure.adapter.rest.exception.constants.ErrorConstants.NOT_FOUND_MESSAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthClientServiceImplTest {

    @Mock
    private AuthClientRepository repository;

    @Mock
    private PasswordEncoder encoder;

    @Spy
    private ClientMapper mapper = Mappers.getMapper(ClientMapper.class);

    @InjectMocks
    private AuthClientServiceImpl service;

    private ClientRecord clientRecord;
    private ClientRecord expectedClientRecord;
    private AuthClient authClient;

    @BeforeEach
    void setUp() {
        clientRecord = new ClientRecord(
                "client-id",
                "client-secret",
                "Test Client",
                null
        );

        authClient = new AuthClient();
        authClient.setClientId("client-id");
        authClient.setClientSecret("encoded-secret");
        authClient.setRedirectUri("Test Client");

        expectedClientRecord = new ClientRecord(
                "client-id",
                "encoded-secret",
                "Test Client",
                null
        );
    }

    @Test
    void shouldCreateClientSuccessfully() {
        when(encoder.encode(clientRecord.clientSecret())).thenReturn("encoded-secret");
        when(repository.save(authClient)).thenReturn(authClient);

        ClientRecord result = service.create(clientRecord);

        assertNotNull(result);
        assertEquals(expectedClientRecord, result);

        verify(mapper).toEntity(clientRecord);
        verify(encoder).encode(clientRecord.clientSecret());
        verify(repository).save(authClient);
        verify(mapper).toRecord(authClient);
    }

    @Test
    void shouldFindClientByClientIdSuccessfully() {
        when(repository.findByClientId("client-id"))
                .thenReturn(Optional.of(authClient));

        AuthClient result = service.findByClientId("client-id");

        assertNotNull(result);
        assertEquals(authClient, result);

        verify(repository).findByClientId("client-id");
    }

    @Test
    void shouldThrowExceptionWhenClientIdNotFound() {
        when(repository.findByClientId("invalid-id"))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> service.findByClientId("invalid-id")
        );

        assertEquals(NOT_FOUND_MESSAGE, exception.getMessage());
        verify(repository).findByClientId("invalid-id");
    }
}