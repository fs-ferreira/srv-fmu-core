package br.com.ferreiradev.fmu.core.presentation.controller;

import br.com.ferreiradev.fmu.core.infrastructure.adapter.rest.exception.ResourceNotFoundException;
import br.com.ferreiradev.fmu.core.infrastructure.adapter.rest.exception.dto.ErrorResponse;
import br.com.ferreiradev.fmu.core.presentation.dto.FishRecord;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Fish", description = "CRUD Endpoints - Fish entity")
@RequestMapping("/fish")
public interface FishControllerApi extends BaseControllerApi {

    @Operation(summary = "List all fishes")
    @ApiResponse(responseCode = "200", description = "Fishes' list found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = FishRecord.class)))
    @ApiResponse(responseCode = "404", description = "No record found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
    @GetMapping
    ResponseEntity<List<FishRecord>> findAll();

    @Operation(summary = "Add a new fish")
    @ApiResponse(responseCode = "200", description = "Fish created",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = FishRecord.class)))
    @PostMapping
    ResponseEntity<FishRecord> create(@RequestBody FishRecord record);
}
