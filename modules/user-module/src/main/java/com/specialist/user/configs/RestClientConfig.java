package com.specialist.user.configs;

import com.specialist.user.infrastructure.ReviewBufferService;
import com.specialist.user.infrastructure.ReviewBufferRestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${api.specialists-directory.base-url}")
    public String BASE_SYSTEM_API_URL;

    @Bean
    public ReviewBufferService reviewBufferRestService() {
        return new ReviewBufferRestClient(RestClient.builder().baseUrl(BASE_SYSTEM_API_URL).build());
    }
}
