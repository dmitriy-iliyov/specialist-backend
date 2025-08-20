package com.specialist.user.infrastructure.rest;

import com.specialist.user.exceptions.ResponseBodyNullException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class ReviewBufferRestClient implements ReviewBufferService {

    @Value("${api.review-buffer.client-id}")
    public String CLIENT_ID;

    @Value("${api.review-buffer.client-secret}")
    public String CLIENT_SECRET;

    private final RestClient restClient;
    private final LoginRestClient loginRestClient;

    @PostConstruct
    public void postConstruct() {
        //login();
    }

    @Override
    public void deleteBatchByIds(Set<UUID> idsToDelete) {
        Map<String, Set<UUID>> body = Map.of("ids", idsToDelete);
        try {
            restClient
                    .method(HttpMethod.DELETE)
                    .uri("/specialists/reviews/buffer/batch")
                    .body(body)
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientResponseException e) {
            handleRestClientResponseException(e);
        }
    }

    @Override
    public void notifyToResend(Set<UUID> idsToResend) {
        Map<String, Set<UUID>> body = Map.of("ids", idsToResend);
        try {
            restClient
                    .post()
                    .uri("/specialists/reviews/buffer/batch")
                    .body(body)
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientResponseException e) {
            handleRestClientResponseException(e);
        }
    }

    private void handleRestClientResponseException(RestClientResponseException e) {
        if (e.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(401))) {
            login();
        }
        log.error("Api responded with {} {}, message: {}", e.getStatusText(), e.getStatusCode(), e.getResponseBodyAsString());
    }

    private void login() {
        try {
            loginRestClient.login(CLIENT_ID, CLIENT_SECRET);
        } catch (Exception e1) {
            if (e1 instanceof ResponseBodyNullException e2) {
                log.error("Api response body is null.");
            } else {
                log.error("Exception when login, {}", e1.getMessage());
            }
        }
    }
}
