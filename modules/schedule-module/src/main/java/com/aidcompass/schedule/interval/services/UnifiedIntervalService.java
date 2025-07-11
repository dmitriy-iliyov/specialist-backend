package com.aidcompass.schedule.interval.services;

import com.aidcompass.schedule.appointment.models.marker.AppointmentMarker;
import com.aidcompass.schedule.exceptions.interval.IntervalNotFoundByIdException;
import com.aidcompass.core.general.GlobalRedisConfig;
import com.aidcompass.schedule.interval.mapper.IntervalMapper;
import com.aidcompass.schedule.interval.models.IntervalEntity;
import com.aidcompass.schedule.interval.models.dto.IntervalResponseDto;
import com.aidcompass.schedule.interval.models.dto.SystemIntervalCreatedDto;
import com.aidcompass.schedule.interval.models.dto.SystemIntervalUpdateDto;
import com.aidcompass.schedule.interval.repository.IntervalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UnifiedIntervalService implements IntervalService, SystemIntervalService {

    private final IntervalRepository repository;
    private final IntervalMapper mapper;
    private final RedisTemplate<String, String> redisTemplate;


    @Caching(
            evict = {
                    @CacheEvict(value = GlobalRedisConfig.INTERVALS_BY_DATE_CACHE,
                                key = "#ownerId + ':' + #result.date()"),
                    @CacheEvict(value = GlobalRedisConfig.INTERVALS_BY_DATE_INTERVAL_CACHE, key = "#ownerId"),
            }
    )
    @Transactional
    @Override
    public IntervalResponseDto save(UUID ownerId, SystemIntervalCreatedDto dto) {
        IntervalEntity entity = repository.save(mapper.toEntity(ownerId, dto));
        return mapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public IntervalResponseDto findById(Long id) {
        return mapper.toDto(repository.findById(id).orElseThrow(IntervalNotFoundByIdException::new));
    }

    @Transactional(readOnly = true)
    @Override
    public IntervalResponseDto findNearestByOwnerId(UUID ownerId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDate start = now.toLocalDate().plusDays(1);
        if (now.toLocalTime().isAfter(LocalTime.of(10, 0))) {
            start = start.plusDays(1);
        }
        LocalDateTime end = now.plusDays(27);
        return mapper.toDto(repository.findFirstByOwnerIdAndDateBetweenOrderByDateAscStartAsc(ownerId, start, end.toLocalDate())
                .orElseThrow(IntervalNotFoundByIdException::new)
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<IntervalResponseDto> findAllNearestByOwnerIdIn(Set<UUID> ownerIds) {
        LocalDateTime now = LocalDateTime.now();
        LocalDate start = now.toLocalDate().plusDays(1);
        if (now.toLocalTime().isAfter(LocalTime.of(10, 0))) {
            start = start.plusDays(1);
        }
        LocalDateTime end = now.plusDays(27);
        return mapper.toDtoList(repository.findAllNearestByOwnerIdIn(ownerIds, start, end.toLocalDate()));
    }

    @Transactional(readOnly = true)
    @Override
    public IntervalResponseDto findByOwnerIdAndStartAndDate(UUID ownerId, LocalDate date, LocalTime start) {
        return mapper.toDto(repository.findByOwnerIdAndDateAndStart(ownerId, date, start).orElse(null));
    }

    @Cacheable(value = GlobalRedisConfig.INTERVALS_BY_DATE_CACHE, key = "#ownerId + ':' + #date")
    @Transactional(readOnly = true)
    @Override
    public List<IntervalResponseDto> findAllByOwnerIdAndDate(UUID ownerId, LocalDate date) {
        List<IntervalEntity> entityList = repository.findAllByOwnerIdAndDate(ownerId, date);
        return mapper.toDtoList(entityList).stream()
                .sorted(Comparator.comparing(IntervalResponseDto::start))
                .toList();
    }

    @Cacheable(value = GlobalRedisConfig.INTERVALS_BY_DATE_INTERVAL_CACHE, key = "#ownerId")
    @Transactional(readOnly = true)
    @Override
    public List<LocalDate> findMonthDatesByOwnerId(UUID ownerId, LocalDate start, LocalDate end) {
        return repository.findAllByOwnerIdAndDateInterval(ownerId, start, end).stream()
                .map(IntervalEntity::getDate)
                .distinct()
                .sorted()
                .toList();
    }

    @Caching(
            evict = {
                    @CacheEvict(value = GlobalRedisConfig.INTERVALS_BY_DATE_CACHE,
                            key = "#dto.getVolunteerId() + ':' + #dto.getDate()"),
                    @CacheEvict(value = GlobalRedisConfig.INTERVALS_BY_DATE_INTERVAL_CACHE, key = "#dto.getVolunteerId()"),
            }
    )
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public Set<IntervalResponseDto> cut(AppointmentMarker dto, Long id) {
        IntervalEntity entity = repository.findById(id).orElseThrow(IntervalNotFoundByIdException::new);
        IntervalResponseDto systemDto = mapper.toDto(entity);

        Set<IntervalResponseDto> responseDtoSet = new HashSet<>();
        SystemIntervalUpdateDto updateDto;
        if (systemDto.start().equals(dto.getStart()) && systemDto.end().isAfter(dto.getEnd())) {
            updateDto = new SystemIntervalUpdateDto(id, dto.getEnd(), systemDto.end(), systemDto.date());
            mapper.updateEntityFromDto(updateDto, entity);
            responseDtoSet.add(mapper.toDto(repository.save(entity)));
        } else if (systemDto.end().equals(dto.getEnd()) && systemDto.start().isBefore(dto.getStart())) {
            updateDto = new SystemIntervalUpdateDto(id, systemDto.start(), dto.getStart(), systemDto.date());
            mapper.updateEntityFromDto(updateDto, entity);
            responseDtoSet.add(mapper.toDto(repository.save(entity)));
        } else {
            updateDto = new SystemIntervalUpdateDto(id, systemDto.start(), dto.getStart(), systemDto.date());
            IntervalEntity newIntervalEntity = new IntervalEntity(entity.getOwnerId(), dto.getEnd(), systemDto.end(), systemDto.date());
            mapper.updateEntityFromDto(updateDto, entity);
            responseDtoSet.addAll(mapper.toDtoList(repository.saveAll(List.of(entity, newIntervalEntity))));
        }
        return responseDtoSet;
    }

    @Caching(
            evict = {
                    @CacheEvict(value = GlobalRedisConfig.INTERVALS_BY_DATE_CACHE,
                            key = "#result.ownerId() + ':' + #result.date()"),
                    @CacheEvict(value = GlobalRedisConfig.INTERVALS_BY_DATE_INTERVAL_CACHE, key = "#result.ownerId()"),
            }
    )
    @Transactional
    @Override
    public IntervalResponseDto deleteByOwnerIdAndId(UUID ownerId, Long id) {
        IntervalResponseDto dto = mapper.toDto(repository.findById(id).orElseThrow(IntervalNotFoundByIdException::new));
        repository.deleteById(id);
        return dto;
    }

    @Caching(
            evict = {
                    @CacheEvict(value = GlobalRedisConfig.INTERVALS_BY_DATE_CACHE,
                            key = "#ownerId + ':' + #date"),
                    @CacheEvict(value = GlobalRedisConfig.INTERVALS_BY_DATE_INTERVAL_CACHE, key = "#ownerId"),
            }
    )
    @Transactional
    @Override
    public void deleteAllByOwnerIdAndDate(UUID ownerId, LocalDate date) {
        repository.deleteAllByOwnerIdAndDate(ownerId, date);
    }

    @Transactional
    @Override
    public void deleteAllByOwnerId(UUID ownerId) {
        repository.deleteAllByOwnerId(ownerId);

        // scan
        Set<String> toInvalidate = redisTemplate.keys(GlobalRedisConfig.INTERVALS_BY_DATE_CACHE + "::" + ownerId + ":*");
        if (toInvalidate != null && !toInvalidate.isEmpty()) {
            redisTemplate.delete(toInvalidate);
        }
        toInvalidate = redisTemplate.keys(GlobalRedisConfig.INTERVALS_BY_DATE_INTERVAL_CACHE + "::" + ownerId);
        if (toInvalidate != null && !toInvalidate.isEmpty()) {
            redisTemplate.delete(toInvalidate);
        }
    }

    @Transactional
    @Override
    public List<Long> deleteBatchBeforeWeakStart(int batchSize) {
        LocalDate weakStart = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        log.info("START deleting intervals batch with weakStart={}, batchSize={}", weakStart, batchSize);

        List<Long> deletedIds = repository.deleteBatchBeforeDate(batchSize, weakStart);

        log.info("END deleting intervals, deleted id list={}", deletedIds);

        return deletedIds;
    }
}
