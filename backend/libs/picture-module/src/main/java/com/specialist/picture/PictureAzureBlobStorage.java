package com.specialist.picture;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.models.BlobErrorCode;
import com.azure.storage.blob.models.BlobStorageException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Named;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class PictureAzureBlobStorage implements PictureStorage {

    private final BlobContainerClient containerClient;
    private final String PICTURE_FILE_NAME_TEMPLATE;
    private final String DEFAULT_PICTURE_NAME;
    private String defaultPictureUrl;

    @EventListener(ApplicationReadyEvent.class)
    @Override
    public void setUpDefaultUrl() {
        defaultPictureUrl = containerClient.getBlobContainerUrl() + "/" + DEFAULT_PICTURE_NAME;
    }

    @Override
    public String save(MultipartFile picture, UUID aggregateId) {
        if (picture == null || picture.isEmpty()) {
            return defaultPictureUrl;
        }
        try {
            BlobClient blobClient = containerClient.getBlobClient(PICTURE_FILE_NAME_TEMPLATE.formatted(aggregateId));
            blobClient.upload(picture.getInputStream(), picture.getSize(), true);
            return blobClient.getBlobUrl();
        } catch (Exception e) {
            log.error("Error when uploading picture to cloud.");
            throw new RuntimeException("Error when uploading picture to cloud:", e);
        }
    }

    @Override
    public Map<UUID, String> saveAll(Map<UUID, MultipartFile> pictures) {
        if (pictures == null || pictures.isEmpty()) {
            return Collections.emptyMap();
        }
        return pictures.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> save(e.getValue(), e.getKey())
                ));
    }

    @Named("resolvePictureUrl")
    @Override
    public String resolvePictureUrl(String pictureUrl) {
        if (pictureUrl != null) {
            return pictureUrl;
        }
        return defaultPictureUrl;
    }

    @Async
    @Override
    public void deleteByAggregateId(UUID aggregateId) {
        try {
            BlobClient blobClient = containerClient.getBlobClient(PICTURE_FILE_NAME_TEMPLATE.formatted(aggregateId));
            blobClient.delete();
        } catch (BlobStorageException e) {
            if (e.getErrorCode() == BlobErrorCode.BLOB_NOT_FOUND) {
                return;
            }
            log.error("Error when deleting picture from cloud storage.", e);
            throw new RuntimeException("Error when deleting picture from cloud storage", e);
        } catch (Exception e) {
            log.error("Error when deleting picture from cloud.");
            throw new RuntimeException("Error when deleting picture from cloud:", e);
        }
    }

    @Async
    @Override
    public void deleteAllByAggregateId(List<UUID> ids) {
        ids.forEach(this::deleteByAggregateId);
    }
}
