package br.com.ferreiradev.fmu.core.presentation.controller.auth.impl;

import br.com.ferreiradev.fmu.core.application.service.AuthUserService;
import br.com.ferreiradev.fmu.core.infrastructure.security.CustomAuthenticationProvider;
import br.com.ferreiradev.fmu.core.presentation.dto.AuthRecord;
import br.com.ferreiradev.fmu.core.presentation.dto.TokenRecord;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.context.SecurityContextRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private CustomAuthenticationProvider authenticationManager;
    @Mock
    private SecurityContextRepository securityContextRepository;
    @Mock
    private AuthUserService service;

    @InjectMocks
    AuthController authController;

    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    private HttpSession session;

    private final String sessionId = "MOCK_SESSION_COOKIE";
    private TokenRecord tokenRecord = new TokenRecord("access_token", "test", "Bearer", 299);
    ;

    @BeforeEach
    public void setup() {
        when(request.getSession(false)).thenReturn(session);
        when(session.getId()).thenReturn(sessionId);
    }

    @Test
    void shouldLoginWithCredentials() {
        AuthRecord record = new AuthRecord("mock-user", "mock-password");

        Authentication authenticated =
                mock(Authentication.class);

        when(authenticationManager.authenticate(any()))
                .thenReturn(authenticated);
        when(service.generateJWT(sessionId)).thenReturn(tokenRecord);

        var result = authController.login(record, request, response);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(result.getBody(), tokenRecord);
        verify(authenticationManager, times(1))
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        verify(securityContextRepository, times(1))
                .saveContext(any(), eq(request), eq(response));
        verify(service, times(1)).generateJWT(sessionId);
    }

    @Test
    void shouldGenerateTokenFromSocialSession() {
        when(service.generateJWT(sessionId)).thenReturn(tokenRecord);

        var result = authController.token(request, response);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(result.getBody(), tokenRecord);
        verify(service, times(1)).generateJWT(sessionId);
    }

    @Test
    void shouldRefreshTokenFromCurrentSession() {
        when(service.refreshJWT(sessionId)).thenReturn(tokenRecord);

        var result = authController.refreshToken(request, response);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(result.getBody(), tokenRecord);
        verify(service, times(1)).refreshJWT(sessionId);
    }
}
