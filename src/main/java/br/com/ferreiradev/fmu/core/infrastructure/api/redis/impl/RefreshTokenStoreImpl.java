package br.com.ferreiradev.fmu.core.infrastructure.api.redis.impl;

import br.com.ferreiradev.fmu.core.infrastructure.api.redis.RefreshTokenStore;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static br.com.ferreiradev.fmu.core.domain.constants.SecurityConstants.REFRESH_TOKEN_TTL;

@Repository
@RequiredArgsConstructor
public class RefreshTokenStoreImpl implements RefreshTokenStore {

    private static final String PREFIX = "auth:refresh:";

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void save(String sessionId, String refreshToken) {
        String key = buildKey(sessionId);

        redisTemplate.opsForValue()
                .set(key, refreshToken, REFRESH_TOKEN_TTL);
    }

    @Override
    public Optional<String> findBySessionId(String sessionId) {
        String key = buildKey(sessionId);

        Object value = redisTemplate.opsForValue().get(key);
        return Optional.ofNullable(value).map(Object::toString);
    }

    @Override
    public void delete(String sessionId) {
        redisTemplate.delete(buildKey(sessionId));
    }

    private String buildKey(String sessionId) {
        return PREFIX + sessionId;
    }
}
