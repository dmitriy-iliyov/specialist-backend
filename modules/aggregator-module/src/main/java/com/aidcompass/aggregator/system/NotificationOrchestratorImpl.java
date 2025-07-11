package com.aidcompass.aggregator.system;

import com.aidcompass.schedule.appointment.models.dto.AppointmentResponseDto;
import com.aidcompass.schedule.appointment.services.SystemAppointmentService;
import com.aidcompass.core.contact.core.models.dto.PublicContactResponseDto;
import com.aidcompass.core.general.contracts.NotificationOrchestrator;
import com.aidcompass.core.general.contracts.dto.BaseSystemVolunteerDto;
import com.aidcompass.core.general.contracts.dto.BatchRequest;
import com.aidcompass.core.general.contracts.dto.BatchResponse;
import com.aidcompass.message.information.GreetingService;
import com.aidcompass.message.information.InformationService;
import com.aidcompass.message.information.dto.AppointmentDto;
import com.aidcompass.message.information.dto.AppointmentReminderDto;
import com.aidcompass.message.information.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationOrchestratorImpl implements NotificationOrchestrator {

//    @Value("${}")
//    private String topic;

    private final SystemAppointmentService systemAppointmentService;
    private final UserAggregator userAggregator;
    private final InformationService informationService;
    private final GreetingService greetingService;
    //private final KafkaTemplate<String, KafkaMessage<UserDto>> kafkaTemplate;


    @Override
    public void greeting(BaseSystemVolunteerDto dto) {
        PublicContactResponseDto contact = userAggregator.findPrimaryContactByOwnerId(dto.id());
        UserDto userDto = new UserDto(
                dto.id(),
                dto.firstName(),
                dto.secondName(),
                dto.lastName(),
                contact.type(),
                contact.contact()
        );
        //kafkaTemplate.send(topic, new KafkaMessage<>(EventType.VOLUNTEER_APPROVED, userDto));
    }

    @Override
    public Boolean remind(BatchRequest batchRequest) {
        BatchResponse<AppointmentResponseDto> batchResponse = systemAppointmentService.findBatchToRemind(
                batchRequest.size(), batchRequest.page()
        );
        Map<UUID, AppointmentDto> appointmentMap = batchResponse.batch().stream()
                .collect(Collectors.toMap(
                        AppointmentResponseDto::customerId,
                        dto -> new AppointmentDto(dto.type().toString(), dto.date(), dto.description()))
                );
        Map<UUID, UserDto> userMap = userAggregator.findAllCustomerByIdIn(appointmentMap.keySet());
        List<AppointmentReminderDto> reminderList = appointmentMap.entrySet().stream()
                .map(entry -> new AppointmentReminderDto(userMap.get(entry.getKey()), entry.getValue()))
                .toList();
        informationService.reminderNotification(reminderList);
        return batchResponse.hasNext();
    }
}
