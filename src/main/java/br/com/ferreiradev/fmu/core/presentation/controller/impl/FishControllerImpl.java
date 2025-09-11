package br.com.ferreiradev.fmu.core.presentation.controller.impl;

import br.com.ferreiradev.fmu.core.application.service.FishService;
import br.com.ferreiradev.fmu.core.presentation.controller.FishControllerApi;
import br.com.ferreiradev.fmu.core.presentation.dto.FishRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FishControllerImpl implements FishControllerApi {

    private final FishService fishService;

    public FishControllerImpl(FishService fishService) {
        this.fishService = fishService;
    }

    @Override
    public ResponseEntity<List<FishRecord>> findAll() {
        return ResponseEntity.ok(fishService.findAll());
    }

    @Override
    public ResponseEntity<FishRecord> create(FishRecord record) {
        return ResponseEntity.ok(fishService.create(record));
    }
}
