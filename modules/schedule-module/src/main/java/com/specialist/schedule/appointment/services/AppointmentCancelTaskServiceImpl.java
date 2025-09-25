package com.specialist.schedule.appointment.services;

import com.specialist.schedule.appointment.models.AppointmentCancelTaskEntity;
import com.specialist.schedule.appointment.models.dto.AppointmentCancelTaskCreateDto;
import com.specialist.schedule.appointment.models.dto.AppointmentCancelTaskResponseDto;
import com.specialist.schedule.appointment.models.enums.AppointmentCancelTaskType;
import com.specialist.schedule.appointment.repositories.AppointmentCancelTaskRepository;
import com.specialist.schedule.config.ScheduleCacheConfig;
import com.specialist.schedule.exceptions.AppointmentCancelTaskNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentCancelTaskServiceImpl implements AppointmentCancelTaskService {

    private final AppointmentCancelTaskRepository repository;
    private final CacheManager cacheManager;

    @Transactional
    @Override
    public void save(AppointmentCancelTaskCreateDto dto) {
        try {
            repository.save(new AppointmentCancelTaskEntity(dto.participantId(), dto.type(), dto.date()));
            Cache cache = cacheManager.getCache(ScheduleCacheConfig.APPOINTMENT_CANCEL_TASK_EXISTS);
            if (cache != null) {
                String key = dto.participantId().toString() + ':' + dto.type().toString() + ':' + dto.date().toString();
                cache.put(key, true);
            }
        } catch (DataIntegrityViolationException e) {
            log.info("Attempt to insert existed entity from dto={}", dto);
        }
    }

    @Cacheable(value = ScheduleCacheConfig.APPOINTMENT_CANCEL_TASK_EXISTS ,
               key = "#participantId + ':' + #type + ':' + #date")
    @Transactional(readOnly = true)
    @Override
    public Boolean existsByParticipantIdAndTypeAndDate(UUID participantId, AppointmentCancelTaskType type,
                                                                                LocalDate date) {
        return repository.existsByParticipantIdAndTypeAndDate(participantId, type, date);
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
    public AppointmentCancelTaskResponseDto findFirstByType(AppointmentCancelTaskType type) {
        AppointmentCancelTaskEntity entity = repository.findFirstByType(type).orElse(null);
        if (entity == null) {
            return null;
        }
        return new AppointmentCancelTaskResponseDto(entity.getId(), entity.getParticipantId(), entity.getType(), entity.getDate());
    }
}
