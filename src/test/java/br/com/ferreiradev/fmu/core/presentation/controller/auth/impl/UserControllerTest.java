package br.com.ferreiradev.fmu.core.presentation.controller.auth.impl;

import br.com.ferreiradev.fmu.core.application.service.UserService;
import br.com.ferreiradev.fmu.core.presentation.dto.UserRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    UserService service;

    @InjectMocks
    private UserController controller;

    @Test
    void shouldCreateUser() {
        UserRecord record = mock(UserRecord.class);
        when(service.create(record)).thenReturn(record);

        ResponseEntity<Void> response = controller.create(record);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNull();

        verify(service).create(record);
        verifyNoMoreInteractions(service);
    }
}
