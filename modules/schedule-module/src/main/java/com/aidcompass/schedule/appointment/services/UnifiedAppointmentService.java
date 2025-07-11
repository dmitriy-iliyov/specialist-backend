package com.aidcompass.schedule.appointment.services;

import com.aidcompass.schedule.appointment.mapper.AppointmentMapper;
import com.aidcompass.schedule.appointment.models.AppointmentEntity;
import com.aidcompass.schedule.appointment.models.dto.AppointmentCreateDto;
import com.aidcompass.schedule.appointment.models.dto.AppointmentResponseDto;
import com.aidcompass.schedule.appointment.models.dto.AppointmentUpdateDto;
import com.aidcompass.schedule.appointment.models.dto.StatusFilter;
import com.aidcompass.schedule.appointment.models.enums.AppointmentAgeType;
import com.aidcompass.schedule.appointment.models.enums.AppointmentStatus;
import com.aidcompass.schedule.appointment.repositories.AppointmentRepository;
import com.aidcompass.schedule.appointment.repositories.AppointmentSpecifications;
import com.aidcompass.schedule.exceptions.appointment.AppointmentNotFoundByIdException;
import com.aidcompass.core.general.GlobalRedisConfig;
import com.aidcompass.core.general.contracts.dto.BatchResponse;
import com.aidcompass.core.general.contracts.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class UnifiedAppointmentService implements AppointmentService, SystemAppointmentService {

    private final AppointmentRepository repository;
    private final AppointmentMapper mapper;
    private final RedisTemplate<String, AppointmentResponseDto> redisTemplate;


    @Caching(
            evict = {
                    @CacheEvict(
                            value = GlobalRedisConfig.APPOINTMENTS_BY_DATE_AND_STATUS_CACHE,
                            key = "#dto.getVolunteerId() + ':' + #dto.getDate() + ':SCHEDULED'"
                    ),
                    @CacheEvict(value = GlobalRedisConfig.APPOINTMENTS_BY_DATE_INTERVAL_CACHE, key = "#dto.getVolunteerId()")
            }
    )
    @Transactional
    @Override
    public AppointmentResponseDto save(UUID customerId, AppointmentCreateDto dto) {
        AppointmentEntity entity = mapper.toEntity(customerId, dto);
        entity.setStatus(AppointmentStatus.SCHEDULED);
        return mapper.toDto(repository.save(entity));
    }

    @Caching(
            evict = {
                    @CacheEvict(
                            value = GlobalRedisConfig.APPOINTMENTS_BY_DATE_AND_STATUS_CACHE,
                            key = "#dto.getVolunteerId() + ':' + #dto.getDate() + ':SCHEDULED'"
                    ),
                    @CacheEvict(value = GlobalRedisConfig.APPOINTMENTS_BY_DATE_INTERVAL_CACHE, key = "#dto.getVolunteerId()"),
                    @CacheEvict(value = GlobalRedisConfig.APPOINTMENTS_CACHE, key = "#dto.getId()")
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

    @Cacheable(value = GlobalRedisConfig.APPOINTMENTS_CACHE, key = "#id")
    @Transactional(readOnly = true)
    @Override
    public AppointmentResponseDto findById(Long id) {
        return mapper.toDto(repository.findById(id).orElseThrow(AppointmentNotFoundByIdException::new));
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<AppointmentResponseDto> findAllByStatusFilter(UUID participantId, StatusFilter filter,
                                                                      int page, int size, boolean forVolunteer) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").and(Sort.by("start")).ascending());

        Specification<AppointmentEntity> specification = Specification
                .where(AppointmentSpecifications.hasStatuses(filter));

        if (forVolunteer) {
            specification = specification.and(AppointmentSpecifications.hasVolunteerId(participantId));
        } else {
            specification = specification.and(AppointmentSpecifications.hasCustomerId(participantId));
        }

        Page<AppointmentEntity> entityPage = repository.findAll(specification, pageable);

        return new PageResponse<>(
                mapper.toDtoList(entityPage.getContent()),
                entityPage.getTotalPages()
        );
    }

    @Cacheable(
            value = GlobalRedisConfig.APPOINTMENTS_BY_DATE_AND_STATUS_CACHE,
            key = "#volunteerId + ':' + #date + ':' + #status"
    )
    @Transactional(readOnly = true)
    @Override
    public List<AppointmentResponseDto> findAllByVolunteerIdAndDateAndStatus(UUID volunteerId, LocalDate date,
                                                                             AppointmentStatus status) {
        return mapper.toDtoList(repository.findAllByVolunteerIdAndDateAndStatus(volunteerId, date, status));
    }

    @Transactional(readOnly = true)
    @Override
    public List<AppointmentResponseDto> findAllByCustomerIdAndDateAndStatus(UUID customerId, LocalDate date,
                                                                            AppointmentStatus status) {
        return mapper.toDtoList(repository.findAllByCustomerIdAndDateAndStatus(customerId, date, status));
    }

    @Cacheable(value = GlobalRedisConfig.APPOINTMENTS_BY_DATE_INTERVAL_CACHE, key = "#volunteerId")
    @Transactional(readOnly = true)
    @Override
    public List<LocalDate> findMonthDatesByVolunteerId(UUID volunteerId, LocalDate start, LocalDate end) {
        return repository.findAllByVolunteerIdAndDateInterval(volunteerId, start, end).stream()
                .map(AppointmentEntity::getDate)
                .toList();
    }

    @Caching(
            evict = {
                    @CacheEvict(
                            value = GlobalRedisConfig.APPOINTMENTS_BY_DATE_AND_STATUS_CACHE,
                            key = "#result.volunteerId() + ':' + #result.date() + ':' + #result.status()"
                    ),
                    @CacheEvict(value = GlobalRedisConfig.APPOINTMENTS_BY_DATE_INTERVAL_CACHE,
                                key = "#result.volunteerId()"
                    )
            },
            put = @CachePut(value = GlobalRedisConfig.APPOINTMENTS_CACHE, key = "#id")
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
                    @CacheEvict(
                            value = GlobalRedisConfig.APPOINTMENTS_BY_DATE_AND_STATUS_CACHE,
                            key = "#result.volunteerId() + ':' + #result.date() + ':' + #result.status()"
                    ),
                    @CacheEvict(value = GlobalRedisConfig.APPOINTMENTS_BY_DATE_INTERVAL_CACHE,
                            key = "#result.volunteerId()"
                    )
            },
            put = @CachePut(value = GlobalRedisConfig.APPOINTMENTS_CACHE, key = "#id")
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
                    @CacheEvict(
                            value = GlobalRedisConfig.APPOINTMENTS_BY_DATE_AND_STATUS_CACHE,
                            key = "#participantId + ':' + #date + ':CANCELED'"
                    ),
                    @CacheEvict(value = GlobalRedisConfig.APPOINTMENTS_BY_DATE_INTERVAL_CACHE, key = "#participantId")
            }
    )
    @Transactional
    @Override
    public void cancelAllByDate(UUID participantId, LocalDate date) {
        List<Long> ids = repository.updateAllStatus(participantId, date, AppointmentStatus.CANCELED);
        if (!ids.isEmpty()) {
            List<String> toInvalidate = ids.stream()
                    .map(id -> GlobalRedisConfig.APPOINTMENTS_KEY_TEMPLATE.formatted(String.valueOf(id)))
                    .toList();
            redisTemplate.delete(toInvalidate);
        }
    }

    @CacheEvict(value = GlobalRedisConfig.APPOINTMENTS_BY_DATE_INTERVAL_CACHE, key = "#participantId")
    @Transactional
    @Override
    public void deleteAll(UUID participantId) {
        List<Long> ids = repository.deleteAllByParticipantId(participantId);
        if (!ids.isEmpty()) {
            List<String> toInvalidate = ids.stream()
                    .map(id -> GlobalRedisConfig.APPOINTMENTS_KEY_TEMPLATE.formatted(String.valueOf(id)))
                    .toList();
            redisTemplate.delete(toInvalidate);
        }
        Set<String> toInvalidate = redisTemplate.keys(
                GlobalRedisConfig.APPOINTMENTS_BY_DATE_AND_STATUS_CACHE + "::" + participantId + ":*:*");
        if (toInvalidate != null && !toInvalidate.isEmpty()) {
            redisTemplate.delete(toInvalidate);
        }
    }

    // invalidate all caches ??
    @Transactional
    @Override
    public List<Long> markBatchAsSkipped(int batchSize) {
        LocalDate dateLimit = LocalDate.now().minusDays(1);

        log.info("START marking appointments as skipped with batchSize={}, dateLimit={}", batchSize, dateLimit);

        List<Long> markedIds = repository.markBatchAsSkipped(batchSize, dateLimit);

        log.info("END marking appointments, marked id list={}", markedIds);

        return markedIds;
    }

    @Transactional(readOnly = true)
    @Override
    public BatchResponse<AppointmentResponseDto> findBatchToRemind(int batchSize, int page) {
        LocalDate scheduledData = LocalDate.now().plusDays(1);

        log.info("START selecting appointments to remind with batchSize={}, page={}, scheduledData={}", batchSize, page, scheduledData);

        Slice<AppointmentEntity> slice = repository.findBatchToRemind(
                scheduledData,
                AppointmentStatus.SCHEDULED,
                Pageable.ofSize(batchSize).withPage(page)
        );

        log.info("END selecting, hasNext={}", slice.hasNext());

        return new BatchResponse<>(
                mapper.toDtoList(slice.getContent()),
                slice.hasNext()
        );
    }
}
