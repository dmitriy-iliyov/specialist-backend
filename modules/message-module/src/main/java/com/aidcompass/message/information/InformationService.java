package com.aidcompass.message.information;


import com.aidcompass.message.information.dto.AppointmentCanceledDto;
import com.aidcompass.message.information.dto.AppointmentReminderDto;
import com.aidcompass.message.information.dto.AppointmentScheduledDto;

import java.util.List;

public interface InformationService {
    void reminderNotification(List<AppointmentReminderDto> dtoList);

    void onScheduleNotification(AppointmentScheduledDto info);

    void onCancelNotification(AppointmentCanceledDto info);
}
