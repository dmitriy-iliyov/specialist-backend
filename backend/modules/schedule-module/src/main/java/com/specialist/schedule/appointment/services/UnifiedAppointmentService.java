package com.specialist.schedule.appointment.services;

import com.specialist.core.config.ScheduleCacheConfig;
import com.specialist.schedule.appointment.infrastructure.AppointmentService;
import com.specialist.schedule.appointment.mapper.AppointmentMapper;
import com.specialist.schedule.appointment.models.AppointmentEntity;
import com.specialist.schedule.appointment.models.dto.AppointmentCreateDto;
import com.specialist.schedule.appointment.models.dto.AppointmentResponseDto;
import com.specialist.schedule.appointment.models.dto.AppointmentUpdateDto;
import com.specialist.schedule.appointment.models.enums.AppointmentAgeType;
import com.specialist.schedule.appointment.models.enums.AppointmentStatus;
import com.specialist.schedule.appointment.models.enums.ProcessStatus;
import com.specialist.schedule.appointment.repositories.AppointmentRepository;
import com.specialist.schedule.exceptions.appointment.AppointmentNotFoundByIdException;
import com.specialist.utils.pagination.BatchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Slice;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class UnifiedAppointmentService implements AppointmentService, SystemAppointmentService {

    @Value("${api.appointments.cancel-batch.size}")
    private int BATCH_SIZE;
    private final AppointmentRepository repository;
    private final AppointmentMapper mapper;
    private final RedisTemplate<String, AppointmentResponseDto> redisTemplate;

    @Caching(evict = {
            @CacheEvict(value = ScheduleCacheConfig.APPOINTMENTS_BY_DATE_AND_STATUS_CACHE,
                    key = "#dto.specialistId() + ':' + #dto.getDate() + ':SCHEDULED'"),
            @CacheEvict(value = ScheduleCacheConfig.APPOINTMENTS_BY_DATE_INTERVAL_CACHE,
                    key = "#dto.specialistId()")
    })
    @Transactional
    @Override
    public AppointmentResponseDto save(UUID userId, AppointmentCreateDto dto) {
        AppointmentEntity entity = mapper.toEntity(userId, dto);
        entity.setStatus(AppointmentStatus.SCHEDULED);
        return mapper.toDto(repository.save(entity));
    }

    @Caching(
            evict = {
                    @CacheEvict(value = ScheduleCacheConfig.APPOINTMENTS_BY_DATE_AND_STATUS_CACHE,
                            key = "#dto.specialistId() + ':' + #dto.getDate() + ':SCHEDULED'"),
                    @CacheEvict(value = ScheduleCacheConfig.APPOINTMENTS_BY_DATE_INTERVAL_CACHE, key = "#dto.specialistId()"),
                    @CacheEvict(value = ScheduleCacheConfig.APPOINTMENTS_CACHE, key = "#dto.getId()")
            }
    )
    @Transactional
    @Override
    public Map<AppointmentAgeType, AppointmentResponseDto> update(AppointmentUpdateDto dto) {
        Map<AppointmentAgeType, AppointmentResponseDto> responseMap = new HashMap<>();
        AppointmentEntity entity = repository.findById(dto.getId()).orElseThrow(AppointmentNotFoundByIdException::new);
        responseMap.put(AppointmentAgeType.OLD, mapper.toDto(entity));
        mapper.updateEntityFromUpdateDto(dto, entity);
        entity = repository.save(entity);
        responseMap.put(AppointmentAgeType.NEW, mapper.toDto(entity));
        return responseMap;
    }

    @Cacheable(value = ScheduleCacheConfig.APPOINTMENTS_CACHE, key = "#id")
    @Transactional(readOnly = true)
    @Override
    public AppointmentResponseDto findById(Long id) {
        return mapper.toDto(repository.findById(id).orElseThrow(AppointmentNotFoundByIdException::new));
    }

    @Cacheable(value = ScheduleCacheConfig.APPOINTMENTS_BY_DATE_AND_STATUS_CACHE,
               key = "#specialistId + ':' + #date + ':' + #status")
    @Transactional(readOnly = true)
    @Override
    public List<AppointmentResponseDto> findAllBySpecialistIdAndDateAndStatus(UUID specialistId, LocalDate date,
                                                                              AppointmentStatus status) {
        return mapper.toDtoList(repository.findAllBySpecialistIdAndDateAndStatus(specialistId, date, status));
    }

    @Transactional(readOnly = true)
    @Override
    public List<AppointmentResponseDto> findAllByUserIdAndDateAndStatus(UUID userId, LocalDate date,
                                                                        AppointmentStatus status) {
        return mapper.toDtoList(repository.findAllByUserIdAndDateAndStatus(userId, date, status));
    }

    @Cacheable(value = ScheduleCacheConfig.APPOINTMENTS_BY_DATE_INTERVAL_CACHE, key = "#specialistId")
    @Transactional(readOnly = true)
    @Override
    public List<LocalDate> findBySpecialistIdAndDateInterval(UUID specialistId, LocalDate start, LocalDate end) {
        return repository.findAllBySpecialistIdAndDateInterval(specialistId, start, end).stream()
                .map(AppointmentEntity::getDate)
                .toList();
    }

    @Caching(
            evict = {
                    @CacheEvict(value = ScheduleCacheConfig.APPOINTMENTS_BY_DATE_AND_STATUS_CACHE,
                            key = "#result.specialistId() + ':' + #result.date() + ':' + #result.status()"),
                    @CacheEvict(value = ScheduleCacheConfig.APPOINTMENTS_BY_DATE_INTERVAL_CACHE,
                                key = "#result.specialistId()")
            },
            put = @CachePut(value = ScheduleCacheConfig.APPOINTMENTS_CACHE, key = "#id")
    )
    @Transactional
    @Override
    public AppointmentResponseDto completeById(Long id, String review) {
        AppointmentEntity entity = repository.findById(id).orElseThrow(AppointmentNotFoundByIdException::new);
        entity.setReview(review);
        entity.setStatus(AppointmentStatus.COMPLETED);
        return mapper.toDto(repository.save(entity));
    }

    @Caching(
            evict = {
                    @CacheEvict(value = ScheduleCacheConfig.APPOINTMENTS_BY_DATE_AND_STATUS_CACHE,
                            key = "#result.specialistId() + ':' + #result.date() + ':' + #result.status()"),
                    @CacheEvict(value = ScheduleCacheConfig.APPOINTMENTS_BY_DATE_INTERVAL_CACHE,
                            key = "#result.specialistId()")
            },
            put = @CachePut(value = ScheduleCacheConfig.APPOINTMENTS_CACHE, key = "#id")
    )
    @Transactional
    @Override
    public AppointmentResponseDto cancelById(Long id) {
        AppointmentEntity entity = repository.findById(id).orElseThrow(AppointmentNotFoundByIdException::new);
        entity.setStatus(AppointmentStatus.CANCELED);
        return mapper.toDto(repository.save(entity));
    }

    @Caching(
            evict = {
                    @CacheEvict(value = ScheduleCacheConfig.APPOINTMENTS_BY_DATE_AND_STATUS_CACHE,
                            key = "#participantId + ':' + #date + ':CANCELED'"),
                    @CacheEvict(value = ScheduleCacheConfig.APPOINTMENTS_BY_DATE_INTERVAL_CACHE, key = "#participantId")
            }
    )
    @Transactional
    @Override
    public BatchResponse<AppointmentResponseDto> cancelBatchByDate(UUID participantId, LocalDate date) {
        List<AppointmentEntity> entityList = repository.updateBatchStatusByStatusAndParticipantIdAndDate(
                BATCH_SIZE, AppointmentStatus.SCHEDULED.getCode(), participantId, date, AppointmentStatus.CANCELED.getCode()
        );
        if (!entityList.isEmpty()) {
            List<String> toInvalidate = entityList.stream()
                    .map(AppointmentEntity::getId)
                    .map(id -> ScheduleCacheConfig.APPOINTMENTS_KEY_TEMPLATE.formatted(String.valueOf(id)))
                    .toList();
            redisTemplate.delete(toInvalidate);
        }
        return new BatchResponse<>(
                mapper.toDtoList(entityList),
                entityList.size() == BATCH_SIZE,
                entityList.size() == BATCH_SIZE ? 1 : null
        );
    }

    @CacheEvict(value = ScheduleCacheConfig.APPOINTMENTS_BY_DATE_INTERVAL_CACHE, key = "#participantId")
    @Transactional
    @Override
    public BatchResponse<AppointmentResponseDto> cancelBatch(UUID participantId) {
        List<AppointmentEntity> entityList = repository.updateBatchStatusByParticipantIdAndStatus(
                BATCH_SIZE, participantId, AppointmentStatus.SCHEDULED.getCode(), AppointmentStatus.CANCELED.getCode()
        );
        if (!entityList.isEmpty()) {
            List<String> toInvalidate = entityList.stream()
                    .map(AppointmentEntity::getId)
                    .map(id -> ScheduleCacheConfig.APPOINTMENTS_KEY_TEMPLATE.formatted(String.valueOf(id)))
                    .toList();
            redisTemplate.delete(toInvalidate);
        }
        Set<String> toInvalidate = redisTemplate.keys(
                ScheduleCacheConfig.APPOINTMENTS_BY_DATE_AND_STATUS_CACHE + "::" + participantId + ":*:*");
        if (toInvalidate != null && !toInvalidate.isEmpty()) {
            redisTemplate.delete(toInvalidate);
        }
        return new BatchResponse<>(
                mapper.toDtoList(entityList),
                entityList.size() == BATCH_SIZE,
                entityList.size() == BATCH_SIZE ? 1 : null
        );
    }

    // DISCUSS invalidate all caches ??
    @Transactional
    @Override
    public List<Long> skipBatch(int batchSize) {
        LocalDate dateLimit = LocalDate.now().minusDays(1);
        log.info("START marking appointments as skipped with batchSize={}, dateLimit={}", batchSize, dateLimit);
        List<Long> markedIds = repository.updateBatchStatusByStatusAndBeforeDate(
                batchSize,
                AppointmentStatus.SCHEDULED.getCode(),
                dateLimit,
                AppointmentStatus.SKIPPED.getCode()
        );
        log.info("END marking appointments, marked id list={}", markedIds);
        return markedIds;
    }

    @Transactional
    @Override
    public BatchResponse<AppointmentResponseDto> findBatchToRemind(int batchSize) {
        LocalDate scheduledData = LocalDate.now().plusDays(1);
        log.info("START selecting appointments to remind with batchSize={}, scheduledData={}", batchSize, scheduledData);
        List<AppointmentEntity> batch = repository.findBatchByDateAndStatusAndProcessStatus(
                scheduledData,
                AppointmentStatus.SCHEDULED,
                ProcessStatus.NONE,
                batchSize,
                ProcessStatus.TRYING_REMIND
        );
        return new BatchResponse<>(
                mapper.toDtoList(batch),
                batch.size() == batchSize,
                1
        );
    }
}
