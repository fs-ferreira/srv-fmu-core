package br.com.ferreiradev.fmu.core.application.service.impl;

import br.com.ferreiradev.fmu.core.application.mapper.FishMapper;
import br.com.ferreiradev.fmu.core.domain.model.Fish;
import br.com.ferreiradev.fmu.core.domain.repository.FishRepository;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FishServiceImplTest {

    @Mock
    private FishRepository repository;

    @Spy
    private FishMapper mapper = Mappers.getMapper(FishMapper.class);

    @InjectMocks
    FishServiceImpl fishService;

    private FishRecord record;
    private Fish entity;

    @BeforeEach
    void setup(){
        record = new FishRecord(1L, "Fish", "SALTWATER");
        entity = mapper.toEntity(record);
        clearInvocations(mapper);
    }

    @Test
    void shouldCreateFishSuccessfully(){
        when(repository.save(any(Fish.class))).thenReturn(entity);

        FishRecord result = fishService.create(record);

        assertNotNull(result);
        assertEquals(result, record);

        verify(mapper).toEntity(record);
        verify(mapper).toRecord(entity);
    }

    @Test
    void shouldFindAllFishSuccessfully() {
        when(repository.findAll()).thenReturn(List.of(entity));

        List<FishRecord> result = fishService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(record, result.getFirst());

        verify(repository).findAll();
        verify(mapper).toRecord(entity);
    }
}
