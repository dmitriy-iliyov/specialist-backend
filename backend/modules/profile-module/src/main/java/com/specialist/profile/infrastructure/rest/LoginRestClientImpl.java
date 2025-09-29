package com.specialist.profile.infrastructure.rest;

import com.specialist.profile.exceptions.ResponseBodyNullException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class LoginRestClientImpl implements LoginRestClient {

    @Value("${api.auth.login-url}")
    public String LOGIN_URI;
    private final RestClient restClient;
    private final BearerTokenRepository repository;

    @Override
    public void login(String clientId, String clientSecret) throws Exception {
        Map<String, String> body = new HashMap<>();
        body.put("client_id", clientId);
        body.put("client_secret", clientSecret);
        try {
            Map<String, Object> rawResponseBody = restClient
                    .post()
                    .uri(LOGIN_URI)
                    .body(body)
                    .retrieve()
                    .body(Map.class);

            if (rawResponseBody == null || rawResponseBody.isEmpty()) {
                throw new ResponseBodyNullException();
            }

            Map<String, String> responseBody = rawResponseBody.entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            e -> e.getValue().toString()
                    ));

            repository.save(UUID.fromString(clientId), responseBody.get("access_token"));
        } catch (RestClientResponseException e) {
            log.error("Api respond with code {} when SERVICE login, body :{}", e.getStatusCode(), e.getResponseBodyAsString());
        }
    }
}
