package br.com.ferreiradev.fmu.core.presentation.controller.auth.impl;

import br.com.ferreiradev.fmu.core.application.service.UserService;
import br.com.ferreiradev.fmu.core.presentation.controller.auth.UserAPI;
import br.com.ferreiradev.fmu.core.presentation.dto.UserRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController implements UserAPI {

    private final UserService service;

    @Override
    public ResponseEntity<Void> create(UserRecord record) {
        service.create(record);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
