package br.com.ferreiradev.fmu.core.presentation.controller.resource.impl;

import br.com.ferreiradev.fmu.core.application.service.FishService;
import br.com.ferreiradev.fmu.core.presentation.dto.FishRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FishControllerTest {
    @Mock
    private FishService fishService;

    @InjectMocks
    private FishController fishController;

    private final FishRecord fishRecord =
            new FishRecord(1L, "Fish", "SALTWATER");

    @Test
    void shouldReturnListOfFish() throws Exception {
       when(fishService.findAll()).thenReturn(List.of(fishRecord));

       ResponseEntity<List<FishRecord>> result = fishController.findAll();

       assertNotNull(result.getBody());
       assertEquals(result.getBody().getFirst(), fishRecord);
    }

    @Test
    void shouldCreateFish() throws Exception {
        when(fishService.create(fishRecord)).thenReturn(fishRecord);

        ResponseEntity<FishRecord> result = fishController.create(fishRecord);

        assertNotNull(result.getBody());
        assertEquals(result.getBody(), fishRecord);
    }

}
