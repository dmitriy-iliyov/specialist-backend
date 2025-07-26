package com.aidcompass.user.repositories;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ProfileImgStorage {
    String save(MultipartFile avatar, UUID userId);
}
