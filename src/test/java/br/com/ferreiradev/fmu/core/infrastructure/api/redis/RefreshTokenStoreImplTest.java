package br.com.ferreiradev.fmu.core.infrastructure.api.redis;

import br.com.ferreiradev.fmu.core.infrastructure.api.redis.impl.RefreshTokenStoreImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Optional;

import static br.com.ferreiradev.fmu.core.domain.constants.SecurityConstants.REFRESH_TOKEN_TTL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RefreshTokenStoreImplTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @InjectMocks
    private RefreshTokenStoreImpl store;

    private final String sessionId = "SESSION_ID";
    private final String refreshToken = "REFRESH_TOKEN";

    private final String expectedKey = "auth:refresh:SESSION_ID";

    @BeforeEach
    void setup() {
        when(redisTemplate.opsForValue())
                .thenReturn(valueOperations);
    }

    @Test
    void shouldSaveRefreshTokenWithTtl() {
        store.save(sessionId, refreshToken);

        verify(valueOperations).set(
                expectedKey,
                refreshToken,
                REFRESH_TOKEN_TTL
        );
    }

    @Test
    void shouldReturnRefreshTokenWhenExists() {
        when(valueOperations.get(expectedKey))
                .thenReturn(refreshToken);

        Optional<String> result =
                store.findBySessionId(sessionId);

        assertTrue(result.isPresent());
        assertEquals(refreshToken, result.get());

        verify(valueOperations).get(expectedKey);
    }

    @Test
    void shouldReturnEmptyWhenRefreshTokenDoesNotExist() {
        when(valueOperations.get(expectedKey))
                .thenReturn(null);

        Optional<String> result =
                store.findBySessionId(sessionId);

        assertTrue(result.isEmpty());

        verify(valueOperations).get(expectedKey);
    }


    @Test
    void shouldDeleteRefreshTokenBySessionId() {
        clearInvocations(redisTemplate.opsForValue());
        store.delete(sessionId);

        verify(redisTemplate).delete(expectedKey);
    }

}