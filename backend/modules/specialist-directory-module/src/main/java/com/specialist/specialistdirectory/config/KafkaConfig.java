//package com.specialist.specialistdirectory.config;
//
//import com.specialist.contracts.profile.CreatorRatingUpdateEvent;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.core.ProducerFactory;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration("specialistDirectoryModuleKafkaConfig")
//public class KafkaConfig {
//
//    @Bean
//    public KafkaTemplate<String, CreatorRatingUpdateEvent> creatorRatingUpdateEventKafkaTemplate(
//            ProducerFactory<String, Object> producerFactory
//    ) {
//        Map<String, Object> properties = new HashMap<>(producerFactory.getConfigurationProperties());
//        properties.put(ProducerConfig.LINGER_MS_CONFIG, 200);
//        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(properties));
//    }
//}
