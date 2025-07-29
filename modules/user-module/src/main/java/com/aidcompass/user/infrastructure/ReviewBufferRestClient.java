package com.aidcompass.user.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class ReviewBufferRestClient implements ReviewBufferService {

    private final RestClient restClient;

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
            log.error("Api responded with {} {}, message: {}", e.getStatusText(), e.getStatusCode(), e.getResponseBodyAsString());
        }
    }
}
