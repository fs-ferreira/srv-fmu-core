package br.com.ferreiradev.fmu.core.infrastructure.api.internal;

import br.com.ferreiradev.fmu.core.domain.model.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OauthAuthorizationGateway {

    private static final String API_SCOPE  = "ADMIN";

    private final RestTemplate restTemplate;

    @Value("${security.oauth2.client.client-id}")
    private String CLIENT_ID;
    @Value("${security.oauth2.client.client-secret}")
    private String CLIENT_SECRET;

    public String authorize(String sessionId) {
        HttpEntity<Void> entity = new HttpEntity<>(buildCodeHeaders(sessionId));

        String authorizeUrl =
                "http://localhost:8080/oauth2/authorize" +
                        "?response_type=code" +
                        "&client_id=" + CLIENT_ID +
                        "&redirect_uri=http://localhost:8080/authorized" +
                        "&scope=" + API_SCOPE;

        ResponseEntity<String> authorizeResponse =
                restTemplate.exchange(
                        authorizeUrl,
                        HttpMethod.GET,
                        entity,
                        String.class
                );

        return authorizeResponse.getBody();
    }

    private HttpHeaders buildCodeHeaders(String sessionId){
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(CLIENT_ID, CLIENT_SECRET);
        headers.add(HttpHeaders.COOKIE, "JSESSIONID=" + sessionId);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        return headers;
    }

    public TokenResponse generateToken(String code, String sessionId) {

        HttpEntity<MultiValueMap<String, String>> tokenEntity =
                new HttpEntity<>(buildTokenFormBody(code), buildTokenHeaders(sessionId));

        return getTokenResponse(tokenEntity);
    }

    private MultiValueMap<String, String> buildTokenFormBody(String code) {
        MultiValueMap<String, String> tokenBody = new LinkedMultiValueMap<>();
        tokenBody.add("grant_type", "authorization_code");
        tokenBody.add("code", code);
        tokenBody.add("redirect_uri", "http://localhost:8080/authorized");

        return tokenBody;
    }

    public TokenResponse refreshToken(String refreshToken, String sessionId) {

        HttpEntity<MultiValueMap<String, String>> tokenEntity =
                new HttpEntity<>(buildRefreshTokenFormBody(refreshToken), buildTokenHeaders(sessionId));

        return getTokenResponse(tokenEntity);
    }


    private MultiValueMap<String, String> buildRefreshTokenFormBody(String refreshToken) {
        MultiValueMap<String, String> tokenBody = new LinkedMultiValueMap<>();
        tokenBody.add("grant_type", "refresh_token");
        tokenBody.add("refresh_token", refreshToken);
        tokenBody.add("client_id", CLIENT_ID);

        return tokenBody;
    }

    private TokenResponse getTokenResponse(HttpEntity<MultiValueMap<String, String>> tokenEntity) {
        return restTemplate.exchange(
                "http://localhost:8080/oauth2/token",
                HttpMethod.POST,
                tokenEntity,
                TokenResponse.class
        ).getBody();
    }

    private HttpHeaders buildTokenHeaders(String sessionId) {
        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.setBasicAuth(CLIENT_ID, CLIENT_SECRET);
        tokenHeaders.add(HttpHeaders.COOKIE, "JSESSIONID=" + sessionId);
        tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        return tokenHeaders;
    }
}
