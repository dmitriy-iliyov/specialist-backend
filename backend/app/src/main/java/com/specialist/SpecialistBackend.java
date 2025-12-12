package com.specialist;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.dmitriyiliyov.springoutbox.config.EnableOutbox;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.specialist")
@EnableJpaRepositories
@EnableTransactionManagement
@EnableAsync
@EnableKafka
@EnableScheduling
@EnableRetry
@EnableOutbox
public class SpecialistBackend {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                .filename(".env")
                .load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
        SpringApplication.run(SpecialistBackend.class, args);
    }
}