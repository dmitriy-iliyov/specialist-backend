package com.specialist.schedule.appointment.services;

import com.specialist.contracts.profile.ProfileType;
import com.specialist.contracts.profile.SystemProfileReadService;
import com.specialist.contracts.profile.dto.UnifiedProfileResponseDto;
import com.specialist.schedule.appointment.models.dto.AppointmentAggregatedResponseDto;
import com.specialist.schedule.appointment.models.dto.AppointmentFilter;
import com.specialist.schedule.appointment.models.dto.AppointmentResponseDto;
import com.specialist.utils.pagination.PageResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserAppointmentAggregateStrategy implements AppointmentAggregateStrategy {

    private final AppointmentQueryService queryService;
    private final SystemProfileReadService profileReadService;

    public UserAppointmentAggregateStrategy(
            @Qualifier("userAppointmentQueryService") AppointmentQueryService queryService,
            @Qualifier("systemSpecialistProfileReadService") SystemProfileReadService profileReadService) {
        this.queryService = queryService;
        this.profileReadService = profileReadService;
    }

    @Override
    public ProfileType getType() {
        return ProfileType.USER;
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<AppointmentAggregatedResponseDto> aggregate(UUID participantId, AppointmentFilter filter) {
        PageResponse<AppointmentResponseDto> pageResponse = queryService.findAllByFilter(participantId, filter);
        Set<UUID> specialistIds = pageResponse.data().stream()
                .map(AppointmentResponseDto::specialistId)
                .collect(Collectors.toSet());
        Map<UUID, UnifiedProfileResponseDto> specialistProfileMap = profileReadService.findAllByIdIn(specialistIds);
        return new PageResponse<>(
                pageResponse.data().stream()
                        .map(dto -> new AppointmentAggregatedResponseDto(
                                dto, specialistProfileMap.get(dto.specialistId()))
                        )
                        .toList(),
                pageResponse.totalPages()
        );
    }
}
