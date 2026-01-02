package br.com.ferreiradev.fmu.core.application.service.impl;

import br.com.ferreiradev.fmu.core.application.mapper.FishingLogMapper;
import br.com.ferreiradev.fmu.core.application.service.FishingLogService;
import br.com.ferreiradev.fmu.core.domain.model.FishingLog;
import br.com.ferreiradev.fmu.core.domain.repository.FishingLogRepository;
import br.com.ferreiradev.fmu.core.infrastructure.adapter.rest.exception.ResourceNotFoundException;
import br.com.ferreiradev.fmu.core.infrastructure.adapter.rest.exception.thrower.annotation.NotEmptyCollection;
import br.com.ferreiradev.fmu.core.presentation.dto.FishingLogRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.ferreiradev.fmu.core.domain.constants.ErrorConstants.NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
public class FishingLogServiceImpl implements FishingLogService {

    private final FishingLogRepository repository;
    private final FishingLogMapper mapper;

    @Override
    @NotEmptyCollection
    public List<FishingLogRecord> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toRecord)
                .toList();
    }

    @Override
    public FishingLogRecord create(FishingLogRecord record) {
        FishingLog log = repository.save(mapper.toEntity(record));

        return mapper.toRecord(findByIdWithFishOrThrow(log.getId()));
    }

    @Override
    public void validateLog(Long id) {
        FishingLog log = findByIdOrThrow(id);
        log.setValidated(true);
        repository.save(log);
    }

    private FishingLog findByIdOrThrow(Long id){
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MESSAGE));
    }

    private FishingLog findByIdWithFishOrThrow(Long id){
        return repository.findByIdWithFish(id).orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MESSAGE));
    }
}
