package com.aidcompass.user.services;

import com.aidcompass.user.repositories.ProfileImgStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileImgServiceImpl implements ProfileImgService {

    private final ProfileImgStorage storage;


    @Override
    public String save(MultipartFile avatar, UUID userId) {
        return storage.save(avatar, userId);
    }
}
