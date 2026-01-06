package br.com.ferreiradev.fmu.core.infrastructure.security;

import br.com.ferreiradev.fmu.core.application.service.UserService;
import br.com.ferreiradev.fmu.core.presentation.dto.UserRecord;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTCustomAuthFilter extends OncePerRequestFilter {

    private final UserService service;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth != null && !isCustomAuth(auth)){
            String username = auth.getName();
            UserRecord user = service.findByUsername(username);

            auth = new CustomAuthentication(user);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }

    private boolean isCustomAuth(Authentication auth) {
        return auth instanceof CustomAuthentication;
        }
}
