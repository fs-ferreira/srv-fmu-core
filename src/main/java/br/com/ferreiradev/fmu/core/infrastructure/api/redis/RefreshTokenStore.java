package br.com.ferreiradev.fmu.core.infrastructure.api.redis;

import java.util.Optional;

public interface RefreshTokenStore {
    void save(String sessionId, String refreshToken);

    Optional<String> findBySessionId(String sessionId);

    void delete(String sessionId);
}
