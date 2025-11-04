package br.com.ferreiradev.fmu.core.presentation.controller.impl;

import br.com.ferreiradev.fmu.core.application.service.FishService;
import br.com.ferreiradev.fmu.core.presentation.controller.FishApi;
import br.com.ferreiradev.fmu.core.presentation.dto.FishRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FishController implements FishApi {

    @Autowired
    private FishService fishService;

    @Override
    public ResponseEntity<List<FishRecord>> findAll() {
        return ResponseEntity.ok(fishService.findAll());
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<FishRecord> create(FishRecord record) {
        return ResponseEntity.ok(fishService.create(record));
    }
}
