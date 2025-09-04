package com.specialist.user.repositories;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.models.BlobErrorCode;
import com.azure.storage.blob.models.BlobStorageException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AvatarAzureBlobStorage implements AvatarStorage {

    private final BlobContainerClient containerClient;
    private static final String AVATAR_FILE_NAME_TEMPLATE = "%s-avatar.jpg";
    private static final String DEFAULT_AVATAR_NAME = "default-avatar.jpg";
    private String defaultAvatarUrl;

    @EventListener(ApplicationReadyEvent.class)
    @Override
    public void setUpDefaultUrl() {
        defaultAvatarUrl = containerClient.getBlobContainerUrl() + "/" + DEFAULT_AVATAR_NAME;
    }

    @Override
    public String save(MultipartFile avatar, UUID userId) {
        if (avatar == null || avatar.isEmpty()) {
            return defaultAvatarUrl;
        }
        try {
            BlobClient blobClient = containerClient.getBlobClient(AVATAR_FILE_NAME_TEMPLATE.formatted(userId));
            blobClient.upload(avatar.getInputStream(), avatar.getSize(), true);
            return blobClient.getBlobUrl();
        } catch (Exception e) {
            log.error("Error when uploading file to cloud.");
            throw new RuntimeException("Error when uploading file to cloud:", e);
        }
    }

    @Override
    public String resolveAvatarUrl(String avatarUrl) {
        if (avatarUrl != null) {
            return avatarUrl;
        }
        return defaultAvatarUrl;
    }

    @Async
    @Override
    public void deleteByUserId(UUID userId) {
        try {
            BlobClient blobClient = containerClient.getBlobClient(AVATAR_FILE_NAME_TEMPLATE.formatted(userId));
            blobClient.delete();
        } catch (BlobStorageException e) {
            if (e.getErrorCode() == BlobErrorCode.BLOB_NOT_FOUND) {
                return;
            }
            log.error("Error when deleting file from cloud storage.", e);
            throw new RuntimeException("Error when deleting file from cloud storage", e);
        } catch (Exception e) {
            log.error("Error when deleting file from cloud.");
            throw new RuntimeException("Error when deleting file from cloud:", e);
        }
    }
}
