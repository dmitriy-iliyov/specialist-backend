package com.specialist.schedule.interval.services;

import com.specialist.core.exceptions.models.BaseNotFoundException;
import com.specialist.core.exceptions.models.PassedListIsToLongException;
import com.specialist.schedule.exceptions.interval.NearestIntervalNotFoundBySpecialistIdException;
import com.specialist.schedule.interval.mapper.NearestIntervalMapper;
import com.specialist.schedule.interval.models.NearestIntervalEntity;
import com.specialist.schedule.interval.models.dto.IntervalResponseDto;
import com.specialist.schedule.interval.models.dto.NearestIntervalDto;
import com.specialist.schedule.interval.repository.NearestIntervalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NearestIntervalServiceImpl implements NearestIntervalService {

    private final NearestIntervalRepository repository;
    private final NearestIntervalMapper mapper;
    private final IntervalService service;


    @Override
    public Map<UUID, NearestIntervalDto> findAll(Set<UUID> specialistIds) {
        if (specialistIds.size() > 10) {
            throw new PassedListIsToLongException();
        }
        Map<UUID, NearestIntervalDto> response = mapper.toDtoList(repository.findAllById(specialistIds)).stream()
                .collect(Collectors.toMap(NearestIntervalDto::specialistId, Function.identity()));
        List<UUID> localSpecialistIds = new ArrayList<>(specialistIds);
        localSpecialistIds.removeAll(response.keySet());
        if (!localSpecialistIds.isEmpty()) {
            List<IntervalResponseDto> nearestIntervalList = service.findAllNearestBySpecialistIdIn(new HashSet<>(localSpecialistIds));
            for (IntervalResponseDto interval: nearestIntervalList) {
                response.put(interval.specialistId(), saveWithTtl(mapper.toEntity(interval)));
            }
        }
        return response;
    }

    @Override
    public NearestIntervalDto findBySpecialistId(UUID specialistId) {
        try {
            return mapper.toDto(repository.findById(specialistId).orElseThrow(
                    NearestIntervalNotFoundBySpecialistIdException::new)
            );
        } catch (BaseNotFoundException e) {
            try {
                IntervalResponseDto dto = service.findNearestBySpecialistId(specialistId);
                return saveWithTtl(mapper.toEntity(dto));
            } catch (BaseNotFoundException e2) {
                return null;
            }
        }
    }

    @Override
    public void deleteBySpecialistId(UUID specialistId, Long id) {
        NearestIntervalDto currentNearest = mapper.toDto(repository.findById(specialistId).orElseThrow(
                NearestIntervalNotFoundBySpecialistIdException::new)
        );
        if (currentNearest.id().equals(id)) {
            repository.deleteById(specialistId);
            try {
                IntervalResponseDto dto = service.findNearestBySpecialistId(specialistId);
                NearestIntervalEntity entity = mapper.toEntity(dto);
                saveWithTtl(entity);
            } catch (BaseNotFoundException ignored) { }
        }
    }

    @Override
    public void replaceIfEarlier(UUID specialistId, IntervalResponseDto dto) {
        try {
            NearestIntervalDto currentNearest = mapper.toDto(repository.findById(specialistId).orElseThrow(
                    NearestIntervalNotFoundBySpecialistIdException::new)
            );
            if (currentNearest.date().isAfter(dto.date()) || currentNearest.start().isAfter(dto.start())) {
                saveWithTtl(mapper.toEntity(dto));
            }
        } catch (BaseNotFoundException ignore) {
            saveWithTtl(mapper.toEntity(dto));
        }
    }

    private NearestIntervalDto saveWithTtl(NearestIntervalEntity entity) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationTime;

        expirationTime = entity.getDate().atTime(LocalTime.of(10, 0)).minusDays(1);
        long ttlSeconds = Duration.between(now, expirationTime).getSeconds();

        if (ttlSeconds > 0) {
            entity.setTtlSeconds(ttlSeconds);
            return mapper.toDto(repository.save(entity));
        }
        return null;
    }
}
