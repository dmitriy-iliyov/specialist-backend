package com.specialist.profile.repositories;

import org.mapstruct.Named;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface AvatarStorage {
    @EventListener(ApplicationReadyEvent.class)
    void setUpDefaultUrl();

    String save(MultipartFile avatar, UUID userId);

    @Named("resolveAvatarUrl")
    String resolveAvatarUrl(String avatarUrl);

    void deleteByUserId(UUID userId);
}
