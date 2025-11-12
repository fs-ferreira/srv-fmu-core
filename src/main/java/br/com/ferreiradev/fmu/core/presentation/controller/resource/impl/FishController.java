package br.com.ferreiradev.fmu.core.presentation.controller.resource.impl;

import br.com.ferreiradev.fmu.core.application.service.FishService;
import br.com.ferreiradev.fmu.core.presentation.controller.resource.FishApi;
import br.com.ferreiradev.fmu.core.presentation.dto.FishRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FishController implements FishApi {

    private final FishService fishService;

    @Override
    public ResponseEntity<List<FishRecord>> findAll() {
        return ResponseEntity.ok(fishService.findAll());
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<FishRecord> create(FishRecord record) {
        return ResponseEntity.ok(fishService.create(record));
    }
}
