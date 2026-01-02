package br.com.ferreiradev.fmu.core.application.service.impl;

import br.com.ferreiradev.fmu.core.application.mapper.TokenResponseMapper;
import br.com.ferreiradev.fmu.core.domain.model.TokenResponse;
import br.com.ferreiradev.fmu.core.infrastructure.adapter.rest.exception.UnauthorizedException;
import br.com.ferreiradev.fmu.core.infrastructure.api.internal.OauthAuthorizationGateway;
import br.com.ferreiradev.fmu.core.infrastructure.api.redis.RefreshTokenStore;
import br.com.ferreiradev.fmu.core.presentation.dto.TokenRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static br.com.ferreiradev.fmu.core.domain.constants.ErrorConstants.EXPIRED_SESSION_MESSAGE;
import static br.com.ferreiradev.fmu.core.domain.constants.ErrorConstants.UNAUTHORIZED_ERROR_MESSAGE;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthUserServiceImplTest {
    @Mock
    private OauthAuthorizationGateway authorizationGateway;

    @Mock
    private RefreshTokenStore refreshTokenStore;

    @Spy
    private TokenResponseMapper mapper = Mappers.getMapper(TokenResponseMapper.class);

    @InjectMocks
    private AuthUserServiceImpl service;

    private final String sessionId = "SESSION_ID";
    private final String authCode = "AUTH_CODE";
    private final String refreshToken = "REFRESH_TOKEN";


    @Test
    void shouldGenerateJwtSuccessfully() {
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken("access");
        tokenResponse.setRefreshToken("refresh");
        tokenResponse.setTokenType("Bearer");
        tokenResponse.setExpiresIn(300);

        TokenRecord record =
                new TokenRecord("access", "refresh", "Bearer", 300);

        when(authorizationGateway.authorize(sessionId))
                .thenReturn(authCode);

        when(authorizationGateway.generateToken(authCode, sessionId))
                .thenReturn(tokenResponse);

        when(mapper.toRecord(tokenResponse))
                .thenReturn(record);

        TokenRecord result = service.generateJWT(sessionId);

        assertNotNull(result);
        assertEquals(record, result);

        verify(authorizationGateway).authorize(sessionId);
        verify(authorizationGateway).generateToken(authCode, sessionId);
        verify(refreshTokenStore).save(sessionId, "refresh");
        verify(mapper).toRecord(tokenResponse);
    }

    @Test
    void shouldThrowUnauthorizedExceptionWhenGenerateJwtFails() {
        when(authorizationGateway.authorize(sessionId))
                .thenThrow(new RuntimeException("OAuth error"));

        UnauthorizedException exception =
                assertThrows(UnauthorizedException.class,
                        () -> service.generateJWT(sessionId));

        assertEquals(UNAUTHORIZED_ERROR_MESSAGE, exception.getMessage());

        verify(authorizationGateway).authorize(sessionId);
        verifyNoInteractions(refreshTokenStore);
        verifyNoInteractions(mapper);
    }

    @Test
    void shouldRefreshJwtSuccessfully() {
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken("new-access");
        tokenResponse.setRefreshToken("new-refresh");
        tokenResponse.setTokenType("Bearer");
        tokenResponse.setExpiresIn(300);

        TokenRecord record =
                new TokenRecord("new-access", "new-refresh", "Bearer", 300);

        when(refreshTokenStore.findBySessionId(sessionId))
                .thenReturn(Optional.of(refreshToken));

        when(authorizationGateway.refreshToken(refreshToken, sessionId))
                .thenReturn(tokenResponse);

        when(mapper.toRecord(tokenResponse))
                .thenReturn(record);

        TokenRecord result = service.refreshJWT(sessionId);

        assertNotNull(result);
        assertEquals(record, result);

        verify(refreshTokenStore).findBySessionId(sessionId);
        verify(authorizationGateway).refreshToken(refreshToken, sessionId);
        verify(refreshTokenStore).save(sessionId, "new-refresh");
        verify(mapper).toRecord(tokenResponse);
    }

    @Test
    void shouldThrowUnauthorizedWhenSessionExpired() {
        when(refreshTokenStore.findBySessionId(sessionId))
                .thenReturn(Optional.empty());

        UnauthorizedException exception =
                assertThrows(UnauthorizedException.class,
                        () -> service.refreshJWT(sessionId));

        assertEquals(EXPIRED_SESSION_MESSAGE, exception.getMessage());

        verify(refreshTokenStore).findBySessionId(sessionId);
        verifyNoInteractions(authorizationGateway);
        verifyNoInteractions(mapper);
    }

    @Test
    void shouldThrowUnauthorizedWhenRefreshFails() {
        when(refreshTokenStore.findBySessionId(sessionId))
                .thenReturn(Optional.of(refreshToken));

        when(authorizationGateway.refreshToken(refreshToken, sessionId))
                .thenThrow(new RuntimeException("OAuth refresh error"));

        UnauthorizedException exception =
                assertThrows(UnauthorizedException.class,
                        () -> service.refreshJWT(sessionId));

        assertEquals(UNAUTHORIZED_ERROR_MESSAGE, exception.getMessage());

        verify(refreshTokenStore).findBySessionId(sessionId);
        verify(authorizationGateway).refreshToken(refreshToken, sessionId);
        verifyNoMoreInteractions(refreshTokenStore);
        verifyNoInteractions(mapper);
    }



}
