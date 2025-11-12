package br.com.ferreiradev.fmu.core.presentation.controller.auth.impl;

import br.com.ferreiradev.fmu.core.application.service.AuthClientService;
import br.com.ferreiradev.fmu.core.presentation.controller.auth.AuthClientAPI;
import br.com.ferreiradev.fmu.core.presentation.dto.ClientRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthClientController implements AuthClientAPI {

    private final AuthClientService service;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> create(ClientRecord record) {
        service.create(record);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
