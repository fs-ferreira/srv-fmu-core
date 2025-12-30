package br.com.ferreiradev.fmu.core.presentation.controller.auth.impl;

import br.com.ferreiradev.fmu.core.infrastructure.security.CustomAuthenticationProvider;
import br.com.ferreiradev.fmu.core.presentation.controller.auth.AuthAPI;
import br.com.ferreiradev.fmu.core.presentation.dto.AuthRecord;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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

    @Override
    public ResponseEntity<Void> login( AuthRecord record,
                                       HttpServletRequest request,
                                       HttpServletResponse response) {
        Authentication auth =
                new UsernamePasswordAuthenticationToken(
                        record.username(),
                        record.password()
                );

        Authentication authenticated =
                authenticationManager.authenticate(auth);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authenticated);

        securityContextRepository.saveContext(context, request, response);

        return ResponseEntity.noContent().build();
    }
}
