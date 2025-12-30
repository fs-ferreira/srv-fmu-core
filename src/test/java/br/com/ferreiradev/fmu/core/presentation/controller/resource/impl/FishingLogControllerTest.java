package br.com.ferreiradev.fmu.core.presentation.controller.resource.impl;

import br.com.ferreiradev.fmu.core.application.service.FishingLogService;
import br.com.ferreiradev.fmu.core.presentation.dto.FishingLogRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FishingLogControllerTest {

    @Mock
    private FishingLogService service;

    @InjectMocks
    private FishingLogController controller;

    @Test
    void shouldReturnAllFishingLogs() {
        List<FishingLogRecord> logs = List.of(
                mock(FishingLogRecord.class),
                mock(FishingLogRecord.class)
        );

        when(service.findAll()).thenReturn(logs);

        ResponseEntity<List<FishingLogRecord>> response = controller.findAll();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(logs);

        verify(service).findAll();
        verifyNoMoreInteractions(service);
    }

    @Test
    void shouldCreateFishingLog() {
        
        FishingLogRecord input = mock(FishingLogRecord.class);
        FishingLogRecord saved = mock(FishingLogRecord.class);

        when(service.create(input)).thenReturn(saved);
        
        ResponseEntity<FishingLogRecord> response = controller.create(input);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(saved);

        verify(service).create(input);
        verifyNoMoreInteractions(service);
    }

    @Test
    void shouldValidateFishingLog() {
        
        Long id = 1L;
        doNothing().when(service).validateLog(id);

        ResponseEntity<Void> response = controller.validate(id);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();

        verify(service).validateLog(id);
        verifyNoMoreInteractions(service);
    }
}
