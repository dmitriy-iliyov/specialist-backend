package com.aidcompass.aggregator.api.appointment;

import com.aidcompass.aggregator.AggregatorUtils;
import com.aidcompass.aggregator.api.appointment.dto.CustomerAppointmentDto;
import com.aidcompass.aggregator.api.appointment.dto.DtoMapper;
import com.aidcompass.aggregator.api.appointment.dto.PublicVolunteerDto;
import com.aidcompass.aggregator.api.appointment.dto.VolunteerAppointmentDto;
import com.aidcompass.schedule.appointment.models.dto.AppointmentResponseDto;
import com.aidcompass.schedule.appointment.models.dto.StatusFilter;
import com.aidcompass.schedule.appointment.services.AppointmentOrchestrator;
import com.aidcompass.schedule.appointment.services.AppointmentService;
import com.aidcompass.users.customer.models.PublicCustomerResponseDto;
import com.aidcompass.users.customer.services.CustomerService;
import com.aidcompass.users.doctor.services.DoctorService;
import com.aidcompass.core.general.contracts.dto.PageResponse;
import com.aidcompass.users.jurist.services.JuristService;
import com.aidcompass.core.security.domain.authority.models.Authority;
import com.aidcompass.core.security.domain.user.models.dto.SystemUserDto;
import com.aidcompass.core.security.domain.user.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentAggregatorService {

    private final AppointmentOrchestrator appointmentOrchestrator;
    private final CustomerService customerService;
    private final DoctorService doctorService;
    private final JuristService juristService;
    private final DtoMapper mapper;
    private final AggregatorUtils utils;
    private final AppointmentService appointmentService;
    private final UserService userService;


    public VolunteerAppointmentDto findFullAppointment(UUID volunteerId, Long id) {
        AppointmentResponseDto appointment = appointmentOrchestrator.findByVolunteerIdAndId(volunteerId, id);
        return new VolunteerAppointmentDto(
                appointment,
                utils.findAvatarUrlByOwnerId(appointment.customerId()),
                customerService.findPublicById(appointment.customerId()),
                utils.findAllContactByOwnerId(appointment.customerId())
        );
    }

    public PageResponse<VolunteerAppointmentDto> findByFilterAndVolunteerId(UUID volunteerId, StatusFilter filter, int page, int size) {
        PageResponse<AppointmentResponseDto> appointments =
                appointmentService.findAllByStatusFilter(volunteerId, filter, page, size, true);
        return new PageResponse<>(
                prepareVolunteerAppointmentDtoList(appointments.data()),
                appointments.totalPage()
        );
    }

    private List<VolunteerAppointmentDto> prepareVolunteerAppointmentDtoList(List<AppointmentResponseDto> appointments) {
        Map<UUID, List<AppointmentResponseDto>> customerIdToAppointmentsMap = appointments.stream()
                .collect(Collectors.groupingBy(AppointmentResponseDto::customerId));

        Map<UUID, PublicCustomerResponseDto> customerDtoMap = customerService
                .findAllByIds(customerIdToAppointmentsMap.keySet()).stream()
                .collect(Collectors.toMap(PublicCustomerResponseDto::id, Function.identity()));

        Map<UUID, String> customerAvatars = utils.findAllAvatarUrlByOwnerIdIn(customerDtoMap.keySet());

        return customerIdToAppointmentsMap.entrySet().stream()
                .flatMap(entry -> {
                    UUID customerId = entry.getKey();
                    PublicCustomerResponseDto customer = customerDtoMap.get(customerId);
                    if (customer == null) {
                        log.error("Customer not found for id: {}", customerId);
                        return Stream.empty();
                    }
                    return entry.getValue().stream()
                            .map(appointment -> new VolunteerAppointmentDto(
                                    appointment,
                                    customerAvatars.get(customerId),
                                    customer,
                                    utils.findAllContactByOwnerId(customerId))
                            );
                })
                .sorted(Comparator
                        .comparing((VolunteerAppointmentDto dto) -> dto.appointment().date())
                        .thenComparing(dto -> dto.appointment().start()))
                .toList();
    }

    public PageResponse<CustomerAppointmentDto> findByFilterAndCustomerId(UUID customerId, StatusFilter filter, int page, int size) {
        PageResponse<AppointmentResponseDto> appointments =
                appointmentService.findAllByStatusFilter(customerId, filter, page, size, false);
        return new PageResponse<>(
                prepareCustomerAppointmentDtoList(customerId, appointments.data()),
                appointments.totalPage()
        );
    }

    private List<CustomerAppointmentDto> prepareCustomerAppointmentDtoList(UUID customerId, List<AppointmentResponseDto> appointments) {
        Map<UUID, List<AppointmentResponseDto>> customerIdToAppointmentsMap = appointments.stream()
                .collect(Collectors.groupingBy(AppointmentResponseDto::volunteerId));

        Set<UUID> volunteerIds = appointments.stream()
                .map(AppointmentResponseDto::volunteerId)
                .collect(Collectors.toSet());

        List<SystemUserDto> userDtoMap = userService.findAllByIdIn(volunteerIds);
        Map<UUID, SystemUserDto> doctorsMap = new HashMap<>();
        Map<UUID, SystemUserDto> juristsMap = new HashMap<>();

        for (SystemUserDto user: userDtoMap) {
            if (user.authorities().contains(Authority.ROLE_DOCTOR)) {
                doctorsMap.put(user.id(), user);
            } else if (user.authorities().contains(Authority.ROLE_JURIST)) {
                juristsMap.put(user.id(), user);
            } else {
                log.error(
                        "Unexpected error when aggregate appointments for customer, user with id={} has authorities={}",
                        user.id(), user.authorities()
                );
            }
        }

        Map<UUID, PublicVolunteerDto> volunteerDtoMap = new HashMap<>();
        doctorService.findAllByIdIn(doctorsMap.keySet())
                .forEach(dto -> volunteerDtoMap.put(dto.id(), mapper.toVolunteerDto(dto)));
        juristService.findAllByIdIn(juristsMap.keySet())
                .forEach(dto -> volunteerDtoMap.put(dto.id(), mapper.toVolunteerDto(dto)));

        Map<UUID, String> volunteerAvatars = utils.findAllAvatarUrlByOwnerIdIn(volunteerIds);

        return customerIdToAppointmentsMap.entrySet().stream()
                .flatMap(entry -> {
                    UUID volunteerId = entry.getKey();
                    PublicVolunteerDto volunteer = volunteerDtoMap.get(volunteerId);
                    if (volunteer == null) {
                        log.error("Volunteer not found for customer with id: {}", customerId);
                        return Stream.empty();
                    }
                    return entry.getValue().stream()
                            .map(appointment -> new CustomerAppointmentDto(
                                    appointment,
                                    volunteerAvatars.get(volunteerId),
                                    volunteer)
                            );
                })
                .sorted(Comparator
                        .comparing((CustomerAppointmentDto dto) -> dto.appointment().date())
                        .thenComparing(dto -> dto.appointment().start()))
                .toList();
    }
}