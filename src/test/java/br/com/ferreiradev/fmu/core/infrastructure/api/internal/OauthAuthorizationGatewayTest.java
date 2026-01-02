package br.com.ferreiradev.fmu.core.infrastructure.api.internal;

import br.com.ferreiradev.fmu.core.domain.model.TokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OauthAuthorizationGatewayTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private OauthAuthorizationGateway gateway;

    private final String sessionId = "SESSION_ID";
    private final String clientId = "client";
    private final String clientSecret = "secret";

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(gateway, "CLIENT_ID", clientId);
        ReflectionTestUtils.setField(gateway, "CLIENT_SECRET", clientSecret);
    }

    @Test
    void shouldAuthorizeAndReturnCode() {
        String expectedCode = "AUTH_CODE";

        ResponseEntity<String> response =
                new ResponseEntity<>(expectedCode, HttpStatus.OK);

        when(restTemplate.exchange(
                contains("/oauth2/authorize"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(response);

        String code = gateway.authorize(sessionId);

        assertEquals(expectedCode, code);

        ArgumentCaptor<HttpEntity<Void>> captor =
                ArgumentCaptor.forClass(HttpEntity.class);

        verify(restTemplate).exchange(
                contains("/oauth2/authorize"),
                eq(HttpMethod.GET),
                captor.capture(),
                eq(String.class)
        );

        HttpHeaders headers = captor.getValue().getHeaders();

        assertTrue(headers.getFirst(HttpHeaders.AUTHORIZATION).startsWith("Basic "));
        assertEquals("JSESSIONID=" + sessionId, headers.getFirst(HttpHeaders.COOKIE));
    }

    @Test
    void shouldGenerateTokenUsingAuthorizationCode() {
        String code = "AUTH_CODE";

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken("access");
        tokenResponse.setRefreshToken("refresh");
        tokenResponse.setTokenType("Bearer");
        tokenResponse.setExpiresIn(300);

        ResponseEntity<TokenResponse> response =
                new ResponseEntity<>(tokenResponse, HttpStatus.OK);

        when(restTemplate.exchange(
                eq("http://localhost:8080/oauth2/token"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(TokenResponse.class)
        )).thenReturn(response);

        TokenResponse result = gateway.generateToken(code, sessionId);

        assertNotNull(result);
        assertEquals("access", result.getAccessToken());
        assertEquals("refresh", result.getRefreshToken());

        ArgumentCaptor<HttpEntity<MultiValueMap<String, String>>> captor =
                ArgumentCaptor.forClass(HttpEntity.class);

        verify(restTemplate).exchange(
                eq("http://localhost:8080/oauth2/token"),
                eq(HttpMethod.POST),
                captor.capture(),
                eq(TokenResponse.class)
        );

        MultiValueMap<String, String> body = captor.getValue().getBody();

        assertEquals("authorization_code", body.getFirst("grant_type"));
        assertEquals(code, body.getFirst("code"));
    }

    @Test
    void shouldRefreshToken() {
        String refreshToken = "REFRESH_TOKEN";

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken("new-access");
        tokenResponse.setRefreshToken("new-refresh");

        ResponseEntity<TokenResponse> response =
                new ResponseEntity<>(tokenResponse, HttpStatus.OK);

        when(restTemplate.exchange(
                eq("http://localhost:8080/oauth2/token"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(TokenResponse.class)
        )).thenReturn(response);

        TokenResponse result = gateway.refreshToken(refreshToken, sessionId);

        assertNotNull(result);
        assertEquals("new-access", result.getAccessToken());

        ArgumentCaptor<HttpEntity<MultiValueMap<String, String>>> captor =
                ArgumentCaptor.forClass(HttpEntity.class);

        verify(restTemplate).exchange(
                eq("http://localhost:8080/oauth2/token"),
                eq(HttpMethod.POST),
                captor.capture(),
                eq(TokenResponse.class)
        );

        MultiValueMap<String, String> body = captor.getValue().getBody();

        assertEquals("refresh_token", body.getFirst("grant_type"));
        assertEquals(refreshToken, body.getFirst("refresh_token"));
    }

    @Test
    void shouldPropagateExceptionWhenOauthFails() {
        when(restTemplate.exchange(
                anyString(),
                any(),
                any(HttpEntity.class),
                eq(TokenResponse.class)
        )).thenThrow(new RestClientException("OAuth error"));

        assertThrows(RestClientException.class,
                () -> gateway.generateToken("CODE", sessionId));
    }

}