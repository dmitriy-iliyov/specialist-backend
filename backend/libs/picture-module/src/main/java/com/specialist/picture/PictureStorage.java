package com.specialist.picture;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface PictureStorage {
    void setUpDefaultUrl();

    String save(MultipartFile avatar, UUID aggregateId);

    Map<UUID, String> saveAll(Map<UUID, MultipartFile> pictures);

    String resolvePictureUrl(String avatarUrl);

    void deleteByAggregateId(UUID aggregateId);

    void deleteAllByAggregateId(List<UUID> ids);
}
