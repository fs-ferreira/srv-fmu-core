package br.com.ferreiradev.fmu.core.presentation.controller.auth;

import br.com.ferreiradev.fmu.core.presentation.controller.BaseApi;
import br.com.ferreiradev.fmu.core.presentation.dto.AuthRecord;
import br.com.ferreiradev.fmu.core.presentation.dto.TokenRecord;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    ResponseEntity<TokenRecord> login(@RequestBody @Valid AuthRecord record, HttpServletRequest httpRequest, HttpServletResponse response);


    @Operation(summary = "Generate token from an Authenticated User",
            description = "Returns TokenRecords after a social auth process")
    @ApiResponse(responseCode = "200", description = "Token Successfully Generated")
    @GetMapping("/token")
    ResponseEntity<TokenRecord> token(HttpServletRequest httpRequest, HttpServletResponse response);

    @Operation(summary = "Refresh token from an Authenticated User",
            description = "Returns TokenRecords with new access_token")
    @ApiResponse(responseCode = "200", description = "Token Successfully Refreshed")
    @GetMapping("/refresh")
    ResponseEntity<TokenRecord> refreshToken(HttpServletRequest httpRequest, HttpServletResponse response);


}