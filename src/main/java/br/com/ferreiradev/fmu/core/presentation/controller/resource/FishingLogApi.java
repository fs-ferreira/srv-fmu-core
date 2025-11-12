package br.com.ferreiradev.fmu.core.presentation.controller.resource;

import br.com.ferreiradev.fmu.core.infrastructure.adapter.rest.exception.dto.ErrorResponse;
import br.com.ferreiradev.fmu.core.presentation.controller.BaseApi;
import br.com.ferreiradev.fmu.core.presentation.dto.FishingLogRecord;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "Fishing Log", description = "Business endpoints - Fishing Log")
@RequestMapping("/fish/log")
public interface FishingLogApi extends BaseApi {

    @Operation(summary = "List all fishing logs")
    @ApiResponse(responseCode = "200", description = "Fishing logs' list found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = FishingLogRecord.class)))
    @ApiResponse(responseCode = "404", description = "No record found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
    @GetMapping
    ResponseEntity<List<FishingLogRecord>> findAll();

    @Operation(summary = "Add a new fishing log")
    @ApiResponse(responseCode = "201", description = "Fishing log created",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = FishingLogRecord.class)))
    @PostMapping
    ResponseEntity<FishingLogRecord> create(@RequestBody FishingLogRecord record);

    @Operation(summary = "Validate fishing log")
    @ApiResponse(responseCode = "204", description = "Fishing log validated")
    @PutMapping("/{id}/validate")
    ResponseEntity<Void> validate(@PathVariable Long id);
}

