package com.specialist.schedule.interval.validation.ownership;

import java.util.UUID;

public interface IntervalOwnershipValidator {
    void validate(UUID specialistId, Long id);
}
