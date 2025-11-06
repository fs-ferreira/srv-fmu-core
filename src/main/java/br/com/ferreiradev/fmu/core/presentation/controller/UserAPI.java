package br.com.ferreiradev.fmu.core.presentation.controller;

import br.com.ferreiradev.fmu.core.presentation.dto.UserRecord;
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

@Tag(name = "User", description = "User Management - Auth")
@RequestMapping("/user")
public interface UserAPI extends BaseApi {

    @Operation(summary = "Create an user")
    @ApiResponse(responseCode = "201", description = "User created",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserRecord.class)))
    @PostMapping
    ResponseEntity<Void> create(@RequestBody @Valid UserRecord record);
}
