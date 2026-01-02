package br.com.ferreiradev.fmu.core.presentation.controller.auth.impl;

import br.com.ferreiradev.fmu.core.application.service.AuthUserService;
import br.com.ferreiradev.fmu.core.infrastructure.security.CustomAuthenticationProvider;
import br.com.ferreiradev.fmu.core.presentation.controller.auth.AuthAPI;
import br.com.ferreiradev.fmu.core.presentation.dto.AuthRecord;
import br.com.ferreiradev.fmu.core.presentation.dto.TokenRecord;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class AuthController implements AuthAPI {

    private final CustomAuthenticationProvider authenticationManager;
    private final SecurityContextRepository securityContextRepository;
    private final AuthUserService service;

    @Override
    public ResponseEntity<TokenRecord> login(AuthRecord record,
                                             HttpServletRequest request,
                                             HttpServletResponse response) {

        securityContextRepository.saveContext(setupAuth(record), request, response);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.generateJWT(getSessionId(request)));
    }

    @Override
    public ResponseEntity<TokenRecord> token(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.generateJWT(getSessionId(request)));
    }

    @Override
    public ResponseEntity<TokenRecord> refreshToken(HttpServletRequest request, HttpServletResponse response) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.refreshJWT(getSessionId(request)));
    }

    private String getSessionId(HttpServletRequest request){
        return request.getSession(false).getId();
    }

    private SecurityContext setupAuth(AuthRecord record) {
        Authentication auth =
                new UsernamePasswordAuthenticationToken(
                        record.username(),
                        record.password()
                );

        Authentication authenticated =
                authenticationManager.authenticate(auth);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authenticated);

        return context;
    }
}
