package com.specialist.schedule.appointment.infrastructure;

import com.specialist.core.config.ScheduleCacheConfig;
import com.specialist.schedule.appointment.mapper.AppointmentCancelTaskMapper;
import com.specialist.schedule.appointment.models.AppointmentCancelTaskEntity;
import com.specialist.schedule.appointment.models.dto.AppointmentCancelTaskCreateDto;
import com.specialist.schedule.appointment.models.dto.AppointmentCancelTaskResponseDto;
import com.specialist.schedule.appointment.models.enums.AppointmentCancelTaskType;
import com.specialist.schedule.appointment.repositories.AppointmentCancelTaskRepository;
import com.specialist.schedule.exceptions.AppointmentCancelTaskNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentCancelTaskServiceImpl implements AppointmentCancelTaskService {

    private final AppointmentCancelTaskRepository repository;
    private final AppointmentCancelTaskMapper mapper;
    private final CacheManager cacheManager;

    @Override
    public void save(Set<AppointmentCancelTaskCreateDto> dtos) {
        try {
            repository.saveAll(mapper.toEntityList(dtos));
//            Cache cache = cacheManager.getCache(ScheduleCacheConfig.APPOINTMENT_CANCEL_TASK_EXISTS);
//            if (cache != null) {
//                String key = dto.participantId().toString() + ':' + dto.type().toString() + ':' + dto.date().toString();
//                cache.put(key, true);
//            }
        } catch (DataIntegrityViolationException e) {
            log.info("Attempt to insert existed entity from dtos={}", dtos);
        }
    }

    @Cacheable(value = ScheduleCacheConfig.APPOINTMENT_CANCEL_TASK_EXISTS , key = "#participantId + ':' + #type + ':' + #date")
    @Transactional(readOnly = true)
    @Override
    public Boolean existsByParticipantIdAndTypeAndDate(UUID participantId, AppointmentCancelTaskType type, LocalDate date) {
        return repository.existsByParticipantIdAndTypeAndDate(participantId, type, date);
    }

    // FIXME cache
    @Transactional(readOnly = true)
    @Override
    public Map<UUID, Boolean> existsByParticipantIdInAndType(Set<UUID> participantIds, AppointmentCancelTaskType type) {
        List<UUID> existsIds = repository.existsByTypeAndParticipantIdIn(participantIds, type);
        return participantIds.stream()
                .collect(Collectors.toMap(Function.identity(),existsIds::contains));
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        AppointmentCancelTaskEntity entity = repository.findById(id).orElseThrow(AppointmentCancelTaskNotFoundException::new);
        repository.deleteById(id);
        Cache cache = cacheManager.getCache(ScheduleCacheConfig.APPOINTMENT_CANCEL_TASK_EXISTS);
        if (cache != null) {
            String key = entity.getParticipantId().toString() + ':' + entity.getType().toString() + ':' + entity.getDate().toString();
            cache.evict(key);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<AppointmentCancelTaskResponseDto> findBatchByType(AppointmentCancelTaskType type, int batchSize) {
        List<AppointmentCancelTaskEntity> entities = repository.findAllByType(type.getCode(), batchSize);
        if (entities == null) {
            return Collections.emptyList();
        }
        return mapper.toDtoList(entities);
    }

    @Transactional
    @Override
    public void deleteBatch(Set<UUID> ids) {
        repository.deleteAllByIdIn(ids);
    }
}
