package com.specialist.auth.configs;

import com.specialist.contracts.auth.AccountDeleteEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration("authModuleKafkaConfig")
public class KafkaConfig {

    @Bean
    public KafkaTemplate<String, AccountDeleteEvent> accountDeleteEventKafkaTemplate(
            ProducerFactory<String, Object> producerFactory) {
        Map<String, Object> properties = new HashMap<>(producerFactory.getConfigurationProperties());
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 200);
        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        properties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 15000);
        properties.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, 60000);
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(properties));
    }
}
