package com.specialist.schedule.appointment.services;

import com.specialist.contracts.profile.ProfileType;
import com.specialist.schedule.appointment.models.dto.AppointmentAggregatedResponseDto;
import com.specialist.schedule.appointment.models.dto.AppointmentFilter;
import com.specialist.schedule.exceptions.NullAppointmentAggregateStrategyException;
import com.specialist.utils.pagination.PageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AppointmentAggregatorImpl implements AppointmentAggregator {

    private final Map<ProfileType, AppointmentAggregateStrategy> strategyMap;

    public AppointmentAggregatorImpl(List<AppointmentAggregateStrategy> strategies) {
        this.strategyMap = strategies.stream()
                .collect(Collectors.toMap(AppointmentAggregateStrategy::getType, Function.identity()));
    }

    @Override
    public PageResponse<AppointmentAggregatedResponseDto> aggregate(UUID participantId, ProfileType profileType,
                                                                    AppointmentFilter filter) {
        AppointmentAggregateStrategy strategy = strategyMap.get(profileType);
        if (strategy == null) {
            log.error("Aggregate strategy not found for profile type = {}, time = {}", profileType, Instant.now());
            throw new NullAppointmentAggregateStrategyException();
        }
        return strategy.aggregate(participantId, filter);
    }
}
