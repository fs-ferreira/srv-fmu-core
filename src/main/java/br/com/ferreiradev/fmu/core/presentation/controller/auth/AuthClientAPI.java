package br.com.ferreiradev.fmu.core.presentation.controller.auth;

import br.com.ferreiradev.fmu.core.presentation.controller.BaseApi;
import br.com.ferreiradev.fmu.core.presentation.dto.ClientRecord;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Auth Clients", description = "Clients Management - Auth")
@RequestMapping("/auth")
public interface AuthClientAPI extends BaseApi {
    @Operation(summary = "Create a client")
    @ApiResponse(responseCode = "201", description = "Auth Client created",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ClientRecord.class)))
    @PostMapping
    ResponseEntity<Void> create(@RequestBody @Valid ClientRecord record);

}