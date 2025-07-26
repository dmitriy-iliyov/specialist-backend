package com.aidcompass.user.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ProfileImgService {
    String save(MultipartFile avatar, UUID userId);
}
