package com.specialist.schedule.interval.validation.ownership;

import com.specialist.schedule.exceptions.interval.IntervalOwnershipException;
import com.specialist.schedule.interval.models.dto.IntervalResponseDto;
import com.specialist.schedule.interval.services.IntervalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
@RequiredArgsConstructor
public class IntervalOwnershipValidatorImpl implements IntervalOwnershipValidator {

    private final IntervalService service;

    @Override
    public void validate(UUID specialistId, Long id) {
        IntervalResponseDto dto = service.findById(id);
        if (!dto.specialistId().equals(specialistId)) {
            throw new IntervalOwnershipException();
        }
    }
}
