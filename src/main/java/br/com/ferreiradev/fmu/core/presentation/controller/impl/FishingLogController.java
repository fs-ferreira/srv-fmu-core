package br.com.ferreiradev.fmu.core.presentation.controller.impl;

import br.com.ferreiradev.fmu.core.application.service.FishingLogService;
import br.com.ferreiradev.fmu.core.presentation.controller.FishingLogApi;
import br.com.ferreiradev.fmu.core.presentation.dto.FishingLogRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FishingLogController implements FishingLogApi {

    private final FishingLogService service;

    @Override
    public ResponseEntity<List<FishingLogRecord>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Override
    public ResponseEntity<FishingLogRecord> create(FishingLogRecord record) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(record));
    }

    @Override
    public ResponseEntity<Void> validate(Long id) {
        service.validateLog(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
