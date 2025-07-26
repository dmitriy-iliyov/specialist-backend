package com.aidcompass.user.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileImgAzureCloudStorage implements ProfileImgStorage {

    @Override
    public String save(MultipartFile avatar, UUID userId) {
        return "";
    }
}
