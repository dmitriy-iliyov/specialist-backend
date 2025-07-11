package com.aidcompass;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.aidcompass")
@EnableJpaRepositories
@EnableTransactionManagement
@EnableAsync
public class AidCompassBackend {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                .filename(".env")
                .load();

        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
        SpringApplication.run(AidCompassBackend.class, args);
    }
}