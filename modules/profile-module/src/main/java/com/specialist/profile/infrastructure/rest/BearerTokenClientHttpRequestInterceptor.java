package com.specialist.profile.infrastructure.rest;

import com.specialist.profile.exceptions.BearerTokenNullException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
public class BearerTokenClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    private final UUID key;
    private final BearerTokenRepository repository;

    @NonNull
    @Override
    public ClientHttpResponse intercept(@NonNull HttpRequest request, @NonNull byte[] body,
                                        @NonNull ClientHttpRequestExecution execution) throws IOException {
        String token = repository.getByKey(key);
        if (token == null || token.isBlank()) {
            throw new BearerTokenNullException();
        }
        request.getHeaders().setBearerAuth(token);
        return execution.execute(request, body);
    }
}
