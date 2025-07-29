package com.aidcompass.user.configs;

import com.aidcompass.user.infrastructure.ReviewBufferService;
import com.aidcompass.user.infrastructure.ReviewBufferRestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${api.specialists-directory.base-url}")
    public String REVIEW_BUFFER_URL;

    @Bean
    public ReviewBufferService reviewBufferRestService() {
        return new ReviewBufferRestClient(
                RestClient.builder().baseUrl(REVIEW_BUFFER_URL).build()
        );
    }
}
