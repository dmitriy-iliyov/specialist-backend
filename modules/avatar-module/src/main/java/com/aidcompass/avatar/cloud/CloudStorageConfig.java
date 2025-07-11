package com.aidcompass.avatar.cloud;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudStorageConfig {

    @Value("${spring.cloud.azure.storage.blob.connection-string}")
    private String blobConnectionString;

    @Value("${spring.cloud.azure.storage.blob.container-name}")
    private String containerName;


    @Bean
    public BlobServiceClient blobServiceClient() {
        return new BlobServiceClientBuilder().connectionString(blobConnectionString).buildClient();
    }

    @Bean
    public BlobContainerClient blobContainerClient(BlobServiceClient blobServiceClient) {
        return blobServiceClient.getBlobContainerClient(containerName);
    }
}
