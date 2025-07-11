package com.aidcompass.schedule.interval.validation.ownership;

import com.aidcompass.schedule.exceptions.interval.IntervalOwnershipException;
import com.aidcompass.schedule.interval.models.dto.IntervalResponseDto;
import com.aidcompass.schedule.interval.services.IntervalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
@RequiredArgsConstructor
public class IntervalOwnershipValidatorImpl implements IntervalOwnershipValidator {

    private final IntervalService service;


    @Override
    public void validate(UUID ownerId, Long id) {
        IntervalResponseDto dto = service.findById(id);
        if (!dto.ownerId().equals(ownerId)) {
            throw new IntervalOwnershipException();
        }
    }
}
