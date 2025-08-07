package com.specialist.user.repositories;

import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface AvatarStorage {
    String save(MultipartFile avatar, UUID userId);

    @Named("resolveAvatarUrl")
    String resolveAvatarUrl(String avatarUrl);

    void deleteByUserId(UUID userId);
}
