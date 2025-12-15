package com.specialist.picture;

import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface PictureStorage {
    void setUpDefaultUrl();

    String save(MultipartFile avatar, UUID aggregateId);

    @Named("resolvePictureUrl")
    String resolvePictureUrl(String avatarUrl);

    void deleteByAggregateId(UUID aggregateId);

    void deleteAllByAggregateId(List<UUID> ids);
}
