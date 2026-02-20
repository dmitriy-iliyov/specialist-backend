package com.specialist.specialistdirectory.config;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.specialist.picture.PictureAzureBlobStorage;
import com.specialist.picture.PictureStorage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpecialistDirectoryAzureBlobConfig {

    @Value("${spring.cloud.azure.storage.blob.container-name}")
    private String containerName;

    @Bean
    public BlobContainerClient reviewBlobContainerClient(BlobServiceClient blobServiceClient) {
        return blobServiceClient.getBlobContainerClient(containerName);
    }

    @Bean
    public PictureStorage reviewPictureStorage(@Qualifier("reviewBlobContainerClient") BlobContainerClient containerClient) {
        return new PictureAzureBlobStorage(
                containerClient,
                "%s-review.jpg",
                null
        );
    }
}
