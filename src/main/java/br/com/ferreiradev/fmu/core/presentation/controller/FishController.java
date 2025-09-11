package br.com.ferreiradev.fmu.core.presentation.controller;

import br.com.ferreiradev.fmu.core.application.service.FishService;
import br.com.ferreiradev.fmu.core.presentation.dto.FishRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fish")
public class FishController {
    @Autowired
    FishService fishService;

    @GetMapping
    ResponseEntity<List<FishRecord>> findAll() {
        return ResponseEntity.ok(fishService.findAll());
    }
}
