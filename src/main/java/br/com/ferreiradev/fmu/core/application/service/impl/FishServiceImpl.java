package br.com.ferreiradev.fmu.core.application.service.impl;

import br.com.ferreiradev.fmu.core.application.service.FishService;
import br.com.ferreiradev.fmu.core.domain.repository.FishRepository;
import br.com.ferreiradev.fmu.core.infrastructure.adapter.rest.exception.thrower.annotation.NotEmptyCollection;
import br.com.ferreiradev.fmu.core.presentation.dto.FishRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FishServiceImpl implements FishService {

    @Autowired
    FishRepository repository;

    @Override
    @NotEmptyCollection
    public List<FishRecord> findAll() {
        return repository.findAll()
                .stream()
                .map(FishRecord::new)
                .toList();
    }
}
