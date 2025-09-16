package com.specialist.profile.configs;

import com.specialist.profile.infrastructure.rest.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Configuration
public class RestClientConfig {

    @Value("${api.specialists-directory.base-url}")
    public String BASE_SYSTEM_API_URL;

    @Value("${api.review-buffer.client-id}")
    public String CLIENT_ID;

    @Bean
    public LoginRestClient loginRestClient(BearerTokenRepository repository) {
        return new LoginRestClientImpl(
                RestClient.builder()
                        .baseUrl(BASE_SYSTEM_API_URL)
                        .build(),
                repository
        );
    }

    @Bean
    public ClientHttpRequestInterceptor clientHttpRequestInterceptor(BearerTokenRepository repository) {
        return new BearerTokenClientHttpRequestInterceptor(UUID.fromString(CLIENT_ID), repository);
    }

    @Bean
    public ReviewBufferService reviewBufferRestService(ClientHttpRequestInterceptor interceptor,
                                                       LoginRestClient loginRestClient) {
        return new ReviewBufferRestClient(
                RestClient.builder()
                        .baseUrl(BASE_SYSTEM_API_URL)
                        .requestInterceptor(interceptor)
                        .build(),
                loginRestClient
        );
    }
}
