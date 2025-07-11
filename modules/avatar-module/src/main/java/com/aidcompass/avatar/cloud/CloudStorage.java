package com.aidcompass.avatar.cloud;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface CloudStorage {
    String saveOrUpdate(UUID userId, MultipartFile image);

    String generateUrlById(UUID userId);

    String findUrlById(UUID userId);

    void delete(UUID userId);

    String getDefaultUrl();

    String saveOrUpdateDefault(MultipartFile image);
}
