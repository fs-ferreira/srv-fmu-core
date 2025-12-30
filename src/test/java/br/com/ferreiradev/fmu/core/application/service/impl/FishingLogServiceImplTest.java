package br.com.ferreiradev.fmu.core.application.service.impl;

import br.com.ferreiradev.fmu.core.application.mapper.FishingLogMapper;
import br.com.ferreiradev.fmu.core.domain.model.FishingLog;
import br.com.ferreiradev.fmu.core.domain.model.enums.FishType;
import br.com.ferreiradev.fmu.core.domain.model.enums.FishingPlace;
import br.com.ferreiradev.fmu.core.domain.repository.FishingLogRepository;
import br.com.ferreiradev.fmu.core.infrastructure.adapter.rest.exception.ResourceNotFoundException;
import br.com.ferreiradev.fmu.core.presentation.dto.FishRecord;
import br.com.ferreiradev.fmu.core.presentation.dto.FishingLogRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FishingLogServiceImplTest {

    @Mock
    private FishingLogRepository repository;

    @Spy
    private FishingLogMapper mapper = Mappers.getMapper(FishingLogMapper.class);

    @InjectMocks
    FishingLogServiceImpl fishingLogService;

    FishingLogRecord fishingLogRecord;
    FishingLog log;

    @BeforeEach()
    void setup(){
        fishingLogRecord = FishingLogRecord.builder()
                .id(1L)
                .fish(new FishRecord(1L, "Fish", FishType.SALTWATER.name()))
                .place(FishingPlace.PLANET_FISH.name())
                .weight(2.2)
                .date(LocalDate.now())
                .build();

        log = mapper.toEntity(fishingLogRecord);

        clearInvocations(mapper);
    }

    @Test
    void shouldCreateFishingLogSuccessfully(){
        when(repository.save(any(FishingLog.class))).thenReturn(log);
        when(repository.findByIdWithFish(1L)).thenReturn(Optional.of(log));

        FishingLogRecord result = fishingLogService.create(fishingLogRecord);

        assertNotNull(result);
        assertEquals(fishingLogRecord, result);

        verify(mapper).toEntity(fishingLogRecord);
        verify(repository).save(any(FishingLog.class));
        verify(mapper).toRecord(log);
    }

    @Test
    void shouldThrowExceptionWhenCreatedLogIsNotFound() {
        when(repository.save(any(FishingLog.class))).thenReturn(log);
        when(repository.findByIdWithFish(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> fishingLogService.create(fishingLogRecord));
    }

    @Test
    void shouldFindAllFishingLogsSuccessfully() {
        when(repository.findAll()).thenReturn(List.of(log));

        List<FishingLogRecord> result = fishingLogService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(fishingLogRecord, result.getFirst());

        verify(repository).findAll();
        verify(mapper).toRecord(log);
    }

    @Test
    void shouldValidateFishingLogSuccessfully() {
        when(repository.findById(1L)).thenReturn(Optional.of(log));
        when(repository.save(any(FishingLog.class))).thenReturn(log);

        fishingLogService.validateLog(1L);

        assertTrue(log.getValidated());

        verify(repository).findById(1L);
        verify(repository).save(log);
    }

    @Test
    void shouldThrowExceptionWhenValidatingNonExistingLog() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> fishingLogService.validateLog(1L));

        verify(repository).findById(1L);
        verify(repository, never()).save(any());
    }
}
