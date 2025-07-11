package com.aidcompass.avatar.cloud;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AzureBlobCloudStorage implements CloudStorage {

    private final BlobContainerClient blobContainerClient;
    private final String AVATAR_NAME_TEMPLATE = "%s-avatar.jpg";
    private final String DEFAULT_AVATAR_NAME_TEMPLATE = "default-avatar.jpg";
    private String AVATAR_URL_TEMPLATE;
    private String DEFAULT_AVATAR_URL;


    @PostConstruct
    public void getContainerPath() {
        AVATAR_URL_TEMPLATE = blobContainerClient.getBlobContainerUrl() + "/" + AVATAR_NAME_TEMPLATE;
        DEFAULT_AVATAR_URL = blobContainerClient.getBlobContainerUrl() + "/" + DEFAULT_AVATAR_NAME_TEMPLATE;
    }

    @Override
    public String saveOrUpdateDefault(MultipartFile image) {
        try {
            BlobClient blobClient = blobContainerClient.getBlobClient(DEFAULT_AVATAR_NAME_TEMPLATE);
            blobClient.upload(image.getInputStream(), image.getSize(), true);
            return blobClient.getBlobUrl();
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image to Azure Blob Storage!", e);
        }
    }

    @Override
    public String getDefaultUrl() {
        return DEFAULT_AVATAR_URL;
    }

    @Override
    public String saveOrUpdate(UUID userId, MultipartFile image) {
        try {
            BlobClient blobClient = blobContainerClient.getBlobClient(AVATAR_NAME_TEMPLATE.formatted(userId));
            blobClient.upload(image.getInputStream(), image.getSize(), true);
            return blobClient.getBlobUrl();
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image to Azure Blob Storage!", e);
        }
    }

    @Override
    public String findUrlById(UUID userId) {
        BlobClient blobClient = blobContainerClient.getBlobClient(AVATAR_NAME_TEMPLATE.formatted(userId));
        if (Boolean.TRUE.equals(blobClient.exists())) {
            return blobClient.getBlobUrl();
        }
        return null;
    }

    @Override
    public String generateUrlById(UUID userId) {
        return AVATAR_URL_TEMPLATE.formatted(userId);
    }

    @Override
    public void delete(UUID userId) {
        BlobClient blobClient = blobContainerClient.getBlobClient(AVATAR_NAME_TEMPLATE.formatted(userId));
        if (blobClient.exists()) {
            blobClient.delete();
        }
    }
}
