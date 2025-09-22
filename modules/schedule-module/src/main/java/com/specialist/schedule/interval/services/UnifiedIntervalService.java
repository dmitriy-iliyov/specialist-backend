package com.specialist.schedule.interval.services;

import com.specialist.schedule.appointment.models.marker.AppointmentMarker;
import com.specialist.schedule.config.ScheduleCacheConfig;
import com.specialist.schedule.exceptions.interval.IntervalNotFoundByIdException;
import com.specialist.schedule.interval.mapper.IntervalMapper;
import com.specialist.schedule.interval.models.IntervalEntity;
import com.specialist.schedule.interval.models.dto.IntervalResponseDto;
import com.specialist.schedule.interval.models.dto.SystemIntervalCreatedDto;
import com.specialist.schedule.interval.models.dto.SystemIntervalUpdateDto;
import com.specialist.schedule.interval.repository.IntervalRepository;
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

    @Caching(evict = {
            @CacheEvict(value = ScheduleCacheConfig.INTERVALS_BY_DATE_CACHE, key = "#specialistId + ':' + #result.date()"),
            @CacheEvict(value = ScheduleCacheConfig.INTERVALS_BY_DATE_INTERVAL_CACHE, key = "#specialistId"),
    })
    @Transactional
    @Override
    public IntervalResponseDto save(UUID specialistId, SystemIntervalCreatedDto dto) {
        IntervalEntity entity = repository.save(mapper.toEntity(specialistId, dto));
        return mapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public IntervalResponseDto findById(Long id) {
        return mapper.toDto(repository.findById(id).orElseThrow(IntervalNotFoundByIdException::new));
    }

    @Transactional(readOnly = true)
    @Override
    public IntervalResponseDto findNearestBySpecialistId(UUID specialistId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDate start = now.toLocalDate().plusDays(1);
        if (now.toLocalTime().isAfter(LocalTime.of(10, 0))) {
            start = start.plusDays(1);
        }
        LocalDateTime end = now.plusDays(27);
        return mapper.toDto(repository.findFirstBySpecialistIdAndDateBetweenOrderByDateAscStartAsc(specialistId, start, end.toLocalDate())
                .orElseThrow(IntervalNotFoundByIdException::new)
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<IntervalResponseDto> findAllNearestBySpecialistIdIn(Set<UUID> specialistIds) {
        LocalDateTime now = LocalDateTime.now();
        LocalDate start = now.toLocalDate().plusDays(1);
        if (now.toLocalTime().isAfter(LocalTime.of(10, 0))) {
            start = start.plusDays(1);
        }
        LocalDateTime end = now.plusDays(27);
        return mapper.toDtoList(repository.findAllNearestBySpecialistIdIn(specialistIds, start, end.toLocalDate()));
    }

    @Transactional(readOnly = true)
    @Override
    public IntervalResponseDto findBySpecialistIdAndStartAndDate(UUID specialistId, LocalDate date, LocalTime start) {
        return mapper.toDto(repository.findBySpecialistIdAndDateAndStart(specialistId, date, start).orElse(null));
    }

    @Cacheable(value = ScheduleCacheConfig.INTERVALS_BY_DATE_CACHE, key = "#specialistId + ':' + #date")
    @Transactional(readOnly = true)
    @Override
    public List<IntervalResponseDto> findAllBySpecialistIdAndDate(UUID specialistId, LocalDate date) {
        List<IntervalEntity> entityList = repository.findAllBySpecialistIdAndDate(specialistId, date);
        return mapper.toDtoList(entityList).stream()
                .sorted(Comparator.comparing(IntervalResponseDto::start))
                .toList();
    }

    @Cacheable(value = ScheduleCacheConfig.INTERVALS_BY_DATE_INTERVAL_CACHE, key = "#specialistId")
    @Transactional(readOnly = true)
    @Override
    public List<LocalDate> findMonthDatesBySpecialistId(UUID specialistId, LocalDate start, LocalDate end) {
        return repository.findAllBySpecialistIdAndDateInterval(specialistId, start, end).stream()
                .map(IntervalEntity::getDate)
                .distinct()
                .sorted()
                .toList();
    }

    @Caching(evict = {
            @CacheEvict(value = ScheduleCacheConfig.INTERVALS_BY_DATE_CACHE, key = "#dto.specialistId() + ':' + #dto.getDate()"),
            @CacheEvict(value = ScheduleCacheConfig.INTERVALS_BY_DATE_INTERVAL_CACHE, key = "#dto.specialistId()"),
    })
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
            IntervalEntity newIntervalEntity = new IntervalEntity(entity.getSpecialistId(), dto.getEnd(), systemDto.end(), systemDto.date());
            mapper.updateEntityFromDto(updateDto, entity);
            responseDtoSet.addAll(mapper.toDtoList(repository.saveAll(List.of(entity, newIntervalEntity))));
        }
        return responseDtoSet;
    }

    @Caching(evict = {
            @CacheEvict(value = ScheduleCacheConfig.INTERVALS_BY_DATE_CACHE, key = "#result.specialistId() + ':' + #result.date()"),
            @CacheEvict(value = ScheduleCacheConfig.INTERVALS_BY_DATE_INTERVAL_CACHE, key = "#result.specialistId()"),
    })
    @Transactional
    @Override
    public IntervalResponseDto deleteBySpecialistIdAndId(UUID specialistId, Long id) {
        IntervalResponseDto dto = mapper.toDto(repository.findById(id).orElseThrow(IntervalNotFoundByIdException::new));
        repository.deleteById(id);
        return dto;
    }

    @Caching(evict = {
            @CacheEvict(value = ScheduleCacheConfig.INTERVALS_BY_DATE_CACHE, key = "#specialistId + ':' + #date"),
            @CacheEvict(value = ScheduleCacheConfig.INTERVALS_BY_DATE_INTERVAL_CACHE, key = "#specialistId"),
    })
    @Transactional
    @Override
    public void deleteAllBySpecialistIdAndDate(UUID specialistId, LocalDate date) {
        repository.deleteAllBySpecialistIdAndDate(specialistId, date);
    }

    @Transactional
    @Override
    public void deleteAllBySpecialistId(UUID specialistId) {
        repository.deleteAllBySpecialistId(specialistId);
        // scan
        Set<String> toInvalidate = redisTemplate.keys(ScheduleCacheConfig.INTERVALS_BY_DATE_CACHE + "::" + specialistId + ":*");
        if (toInvalidate != null && !toInvalidate.isEmpty()) {
            redisTemplate.delete(toInvalidate);
        }
        toInvalidate = redisTemplate.keys(ScheduleCacheConfig.INTERVALS_BY_DATE_INTERVAL_CACHE + "::" + specialistId);
        if (toInvalidate != null && !toInvalidate.isEmpty()) {
            redisTemplate.delete(toInvalidate);
        }
    }

    @Transactional
    @Override
    public List<Long> deleteBatchBeforeWeekStart(int batchSize) {
        LocalDate weakStart = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        log.info("START deleting intervals batch with weakStart={}, batchSize={}", weakStart, batchSize);
        List<Long> deletedIds = repository.deleteBatchBeforeDate(batchSize, weakStart);
        log.info("END deleting intervals, deleted id list={}", deletedIds);
        return deletedIds;
    }
}
