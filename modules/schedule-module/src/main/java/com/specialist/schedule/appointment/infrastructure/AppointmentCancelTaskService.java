package com.specialist.schedule.appointment.infrastructure;

import com.specialist.schedule.appointment.models.dto.AppointmentCancelTaskCreateDto;
import com.specialist.schedule.appointment.models.dto.AppointmentCancelTaskResponseDto;
import com.specialist.schedule.appointment.models.enums.AppointmentCancelTaskType;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface AppointmentCancelTaskService {
    void save(Set<AppointmentCancelTaskCreateDto> dtos);

    Boolean existsByParticipantIdAndTypeAndDate(UUID participantId, AppointmentCancelTaskType type, LocalDate date);

    Map<UUID, Boolean> existsByParticipantIdInAndType(Set<UUID> participantIds, AppointmentCancelTaskType type);

    void deleteById(UUID id);

    List<AppointmentCancelTaskResponseDto> findBatchByType(AppointmentCancelTaskType type, int batchSize);

    void deleteBatch(Set<UUID> ids);
}
