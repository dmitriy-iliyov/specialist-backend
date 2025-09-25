package com.specialist.schedule.appointment.services;

import com.specialist.contracts.profile.ProfileType;
import com.specialist.contracts.profile.SystemProfileService;
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
public class SpecialistAppointmentAggregateStrategy implements AppointmentAggregateStrategy {

    private final AppointmentQueryService queryService;
    private final SystemProfileService profileService;

    public SpecialistAppointmentAggregateStrategy(
            @Qualifier("specialistAppointmentQueryService") AppointmentQueryService queryService,
            @Qualifier("systemUserProfileService") SystemProfileService profileService) {
        this.queryService = queryService;
        this.profileService = profileService;
    }

    @Override
    public ProfileType getType() {
        return ProfileType.SPECIALIST;
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<AppointmentAggregatedResponseDto> aggregate(UUID participantId, AppointmentFilter filter) {
        PageResponse<AppointmentResponseDto> pageResponse = queryService.findAllByFilter(participantId, filter);
        Set<UUID> userIds = pageResponse.data().stream()
                .map(AppointmentResponseDto::userId)
                .collect(Collectors.toSet());
        Map<UUID, UnifiedProfileResponseDto> userProfileMap = profileService.findAllByIdIn(userIds);
        return new PageResponse<>(
                pageResponse.data().stream()
                        .map(dto -> new AppointmentAggregatedResponseDto(
                                dto, userProfileMap.get(dto.userId()))
                        )
                        .toList(),
                pageResponse.totalPages()
        );
    }
}
