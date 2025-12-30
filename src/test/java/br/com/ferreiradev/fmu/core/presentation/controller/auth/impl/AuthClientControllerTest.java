package br.com.ferreiradev.fmu.core.presentation.controller.auth.impl;

import br.com.ferreiradev.fmu.core.application.service.AuthClientService;
import br.com.ferreiradev.fmu.core.presentation.dto.ClientRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthClientControllerTest {

    @Mock
    private AuthClientService service;

    @InjectMocks
    private AuthClientController controller;

    @Test
    void shouldCreateClient() {
        ClientRecord record = mock(ClientRecord.class);
        when(service.create(record)).thenReturn(record);

        ResponseEntity<Void> response = controller.create(record);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNull();

        verify(service).create(record);
        verifyNoMoreInteractions(service);
    }
}
