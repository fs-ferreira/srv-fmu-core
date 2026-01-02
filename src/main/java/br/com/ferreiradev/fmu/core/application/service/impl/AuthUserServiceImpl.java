package br.com.ferreiradev.fmu.core.application.service.impl;

import br.com.ferreiradev.fmu.core.application.mapper.TokenResponseMapper;
import br.com.ferreiradev.fmu.core.application.service.AuthUserService;
import br.com.ferreiradev.fmu.core.domain.model.TokenResponse;
import br.com.ferreiradev.fmu.core.infrastructure.adapter.rest.exception.UnauthorizedException;
import br.com.ferreiradev.fmu.core.infrastructure.api.internal.OauthAuthorizationGateway;
import br.com.ferreiradev.fmu.core.infrastructure.api.redis.RefreshTokenStore;
import br.com.ferreiradev.fmu.core.presentation.dto.TokenRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.ferreiradev.fmu.core.domain.constants.ErrorConstants.EXPIRED_SESSION_MESSAGE;
import static br.com.ferreiradev.fmu.core.domain.constants.ErrorConstants.UNAUTHORIZED_ERROR_MESSAGE;

@RequiredArgsConstructor
@Service
public class AuthUserServiceImpl implements AuthUserService {

    private final OauthAuthorizationGateway authorizationGateway;
    private final RefreshTokenStore refreshTokenStore;
    private final TokenResponseMapper mapper;

    @Override
    public TokenRecord generateJWT(String sessionId) {
        try {
            String code = authorizationGateway.authorize(sessionId);

            TokenResponse response = authorizationGateway.generateToken(code, sessionId);

            refreshTokenStore.save(sessionId, response.getRefreshToken());

            return mapper.toRecord(response);
        } catch (Exception ex) {
            throw new UnauthorizedException(UNAUTHORIZED_ERROR_MESSAGE, ex);
        }
    }

    @Override
    public TokenRecord refreshJWT(String sessionId) {
        String refreshToken = refreshTokenStore.findBySessionId(sessionId)
                .orElseThrow(() -> new UnauthorizedException(EXPIRED_SESSION_MESSAGE));

        try {
            TokenResponse tokenResponse = authorizationGateway.refreshToken(refreshToken, sessionId);

            refreshTokenStore.save(sessionId, tokenResponse.getRefreshToken());

            return mapper.toRecord(tokenResponse);
        } catch (Exception ex) {
            throw new UnauthorizedException(UNAUTHORIZED_ERROR_MESSAGE, ex);
        }
    }


}
