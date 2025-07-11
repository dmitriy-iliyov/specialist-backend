package com.aidcompass.aggregator.system.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

//    @Value("${}")
//    public String bootstrapServers;
//
//
//    @Bean
//    public <K, V> ProducerFactory<K, V> producerFactory() {
//        Map<String, Object> properties = new HashMap<>();
//        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
//        return new DefaultKafkaProducerFactory<>(properties);
//    }
//
//    @Bean
//    public <K, V> KafkaTemplate<K, V> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }
}
