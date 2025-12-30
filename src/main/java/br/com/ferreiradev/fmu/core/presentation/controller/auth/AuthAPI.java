package br.com.ferreiradev.fmu.core.presentation.controller.auth;

import br.com.ferreiradev.fmu.core.presentation.controller.BaseApi;
import br.com.ferreiradev.fmu.core.presentation.dto.AuthRecord;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Auth endpoints", description = "User Authentication")
@RequestMapping("/api/auth")
public interface AuthAPI extends BaseApi {
    @Operation(summary = "Authenticate an User",
            description = "Authenticate user and return authentication token")
    @ApiResponse(responseCode = "200", description = "User Successfully Authenticated",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AuthRecord.class)))
    @PostMapping("/login")
    ResponseEntity<Void> login(@RequestBody @Valid AuthRecord record, HttpServletRequest httpRequest, HttpServletResponse response);

}