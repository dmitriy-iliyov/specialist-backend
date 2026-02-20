//package com.specialist.notification;
//
//import com.specialist.contracts.notification.ExternalAppointmentCancelEvent;
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
//@Configuration("notificationModuleKafkaConfig")
//public class KafkaConfig {
//
//    @Bean
//    public KafkaTemplate<String, ExternalAppointmentCancelEvent> externalAppointmentCancelEventKafkaTemplate(
//            ProducerFactory<String, Object> producerFactory) {
//        Map<String, Object> properties = new HashMap<>(producerFactory.getConfigurationProperties());
//        properties.put(ProducerConfig.LINGER_MS_CONFIG, 300);
//        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 18 * 1024);
//        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(properties));
//    }
//}