package com.specialist.schedule.appointment.infrastructure;

import com.specialist.schedule.appointment.models.enums.AppointmentCancelTaskType;

public interface AppointmentCancelTaskOrchestrator {
    void cancelBatch(AppointmentCancelTaskType taskType);
}
