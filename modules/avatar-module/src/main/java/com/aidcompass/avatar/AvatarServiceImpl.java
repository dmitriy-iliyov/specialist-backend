package com.aidcompass.avatar;

import com.aidcompass.avatar.cloud.CloudStorage;
import com.aidcompass.avatar.exceptions.AvatarNotFoundByUserIdException;
import com.aidcompass.core.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.core.general.exceptions.models.PassedListIsToLongException;
import com.aidcompass.core.general.utils.uuid.UuidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static com.aidcompass.core.general.utils.uuid.UuidUtils.hashUuidCollection;

@Service
@RequiredArgsConstructor
public class AvatarServiceImpl implements AvatarService {

    private final AvatarRepository repository;
    private final CloudStorage cloudStorage;
    private final CacheManager cacheManager;


    @CachePut(value = "avatars:url", key = "#userId")
    @Transactional
    @Override
    public String saveOrUpdate(UUID userId, MultipartFile image) {
        String url = cloudStorage.saveOrUpdate(userId, image);
        AvatarEntity entity;
        try {
            entity = repository.findByUserId(userId).orElseThrow(AvatarNotFoundByUserIdException::new);
            entity.setAvatarUrl(url);
            entity.setUpdatedAt(Instant.now());
        } catch (BaseNotFoundException e) {
            entity = new AvatarEntity(userId, url);
        }
        return repository.save(entity).getAvatarUrl();
    }

    @Override
    public String saveOrUpdateDefault(MultipartFile image) {
        return cloudStorage.saveOrUpdateDefault(image);
    }

    @Transactional(readOnly = true)
    @Override
    public Map<UUID, String> findAllUrlByOwnerIdIn(Set<UUID> userIdList) {
        if (userIdList.size() > 10) {
            throw new PassedListIsToLongException();
        }
        Cache cache = cacheManager.getCache("avatars:url:map");
        String hash;
        if (cache != null ) {
            hash = hashUuidCollection(userIdList);
            Map<String, String> fromCache = cache.get(hash, Map.class);
            if (fromCache != null) {
                return fromCache.entrySet().stream()
                        .collect(Collectors.toMap(
                                e -> UUID.fromString(e.getKey()),
                                Map.Entry::getValue)
                        );
            }
        }

        Map<UUID, String> urls = new HashMap<>();
        List<UUID> entityList = repository.findAllByUserIdIn(userIdList).stream().map(AvatarEntity::getUserId).toList();
        for (UUID id : userIdList) {
            if (entityList.contains(id)) {
                urls.put(id, cloudStorage.generateUrlById(id));
            } else {
                urls.put(id, cloudStorage.getDefaultUrl());
            }
        }

        if (cache != null) {
            hash = UuidUtils.hashUuidCollection(userIdList);
            Map<String, String> preparedToCache = urls.entrySet().stream()
                    .filter(e -> e.getValue() != null)
                    .collect(Collectors.toMap(
                            e -> e.getKey().toString(),
                            Map.Entry::getValue)
                    );
            cache.put(hash, preparedToCache);
        }
        return urls;
    }

    @Transactional(readOnly = true)
    @Override
    public String findUrlByUserId(UUID userId) {
        Cache cache = cacheManager.getCache("avatars:url");
        String url;
        try {
            if (cache != null) {
                String fromCache = cache.get(userId, String.class);
                if (fromCache != null) {
                    return fromCache;
                }
            }
            url = repository.findByUserId(userId).orElseThrow(AvatarNotFoundByUserIdException::new).getAvatarUrl();
        } catch (BaseNotFoundException ignore) {
            url = cloudStorage.getDefaultUrl();
        }
        if (cache != null) {
            cache.put(userId, url);
        }
        return url;
    }

    @CacheEvict(value = "avatars:url", key = "#userId")
    @Transactional
    @Override
    public void delete(UUID userId) {
        cloudStorage.delete(userId);
        repository.deleteByUserId(userId);
    }
}
