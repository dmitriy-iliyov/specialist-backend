package com.specialist.schedule.appointment_duration;

import com.specialist.core.config.ScheduleCacheConfig;
import com.specialist.core.exceptions.models.PassedListIsToLongException;
import com.specialist.schedule.appointment_duration.models.AppointmentDurationEntity;
import com.specialist.schedule.exceptions.appointment_duration.DurationNotFoundBySpecialistIdException;
import com.specialist.utils.UuidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class  AppointmentDurationServiceImpl implements AppointmentDurationService {

    private final AppointmentDurationRepository repository;
    private final CacheManager cacheManager;

    @CachePut(value = ScheduleCacheConfig.APPOINTMENT_DURATION_CACHE, key = "#specialistId")
    @Transactional
    @Override
    public Long set(UUID specialistId, Long duration) {
        try {
            AppointmentDurationEntity entity = repository.findBySpecialistId(specialistId).orElseThrow(DurationNotFoundBySpecialistIdException::new);
            entity.setDuration(duration);
            duration = repository.save(entity).getDuration();
        } catch (DurationNotFoundBySpecialistIdException e) {
            AppointmentDurationEntity entity = repository.save(new AppointmentDurationEntity(specialistId, duration));
            duration = entity.getDuration();
        }
        return duration;
    }

    @Cacheable(value = ScheduleCacheConfig.APPOINTMENT_DURATION_CACHE, key = "#specialistId", unless = "#result == null")
    @Transactional(readOnly = true)
    @Override
    public Long findBySpecialistId(UUID specialistId) {
//        Cache cache = cacheManager.getCache(GlobalRedisConfig.APPOINTMENT_DURATION_CACHE);
//        Number duration = Objects.requireNonNull(cache).get(specialistId, Number.class);
//        if (duration != null) {
//            return duration.longValue();
//        }
        return repository
                .findBySpecialistId(specialistId)
                .orElseThrow(DurationNotFoundBySpecialistIdException::new)
                .getDuration();
    }

    @Transactional(readOnly = true)
    @Override
    public Map<UUID, Long> findAllBySpecialistIdIn(Set<UUID> specialistIds) {
        if (specialistIds.size() > 10) {
            throw new PassedListIsToLongException();
        }
        Cache cache = cacheManager.getCache(ScheduleCacheConfig.APPOINTMENT_DURATION_MAP_CACHE);
        String hash = UuidUtils.hashUuidCollection(specialistIds);
        if (cache != null) {
            Map<String, String> fromCache = cache.get(hash, Map.class);
            if (fromCache != null) {
                return fromCache.entrySet().stream()
                        .collect(Collectors.toMap(
                                e -> UUID.fromString(e.getKey()),
                                e -> Long.valueOf(e.getValue()))
                        );
            }
        }
        Map<UUID, Long> responseMap = new HashMap<>();
        List<AppointmentDurationEntity> entityList = repository.findAllBySpecialistIdIn(specialistIds);
        entityList.forEach(entity -> responseMap.put(entity.getSpecialistId(), entity.getDuration()));

        if (cache != null) {
            Map<String, String> preparedToCache = responseMap.entrySet().stream()
                    .collect(Collectors.toMap(
                            e -> e.getKey().toString(),
                            e -> e.getValue().toString())
                    );
            cache.put(hash, preparedToCache);
        }

        return responseMap;
    }

    @CacheEvict(value = ScheduleCacheConfig.APPOINTMENT_DURATION_CACHE, key = "#specialistId")
    @Transactional
    @Override
    public void deleteBySpecialistId(UUID specialistId) {
        repository.deleteBySpecialistId(specialistId);
    }

    @Transactional
    @Override
    public void deleteAllBySpecialistIds(Set<UUID> specialistIds) {
        repository.deleteAllBySpecialistIdIn(specialistIds);
        Cache cache = cacheManager.getCache(ScheduleCacheConfig.APPOINTMENT_DURATION_CACHE);
        if (cache != null) {
            specialistIds.forEach(cache::evict);
        }
    }
}
