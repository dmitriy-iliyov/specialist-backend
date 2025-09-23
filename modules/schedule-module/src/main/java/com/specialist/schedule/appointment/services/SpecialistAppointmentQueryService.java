package com.specialist.schedule.appointment.services;

import com.specialist.schedule.appointment.mapper.AppointmentMapper;
import com.specialist.schedule.appointment.models.AppointmentEntity;
import com.specialist.schedule.appointment.models.dto.AppointmentFilter;
import com.specialist.schedule.appointment.models.dto.AppointmentResponseDto;
import com.specialist.schedule.appointment.repositories.AppointmentRepository;
import com.specialist.schedule.appointment.repositories.AppointmentSpecifications;
import com.specialist.schedule.appointment.repositories.PageableUtils;
import com.specialist.utils.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpecialistAppointmentQueryService implements AppointmentQueryService {

    private final AppointmentRepository repository;
    private final AppointmentMapper mapper;

    @Transactional(readOnly = true)
    @Override
    public PageResponse<AppointmentResponseDto> findAllByFilter(UUID participantId, AppointmentFilter filter) {
        Pageable pageable = PageableUtils.generatePageable(filter);
        Specification<AppointmentEntity> specification = Specification
                .where(AppointmentSpecifications.hasStatuses(filter))
                .and(AppointmentSpecifications.hasSpecialistId(participantId));
        Page<AppointmentEntity> entityPage = repository.findAll(specification, pageable);
        return new PageResponse<>(
                mapper.toDtoList(entityPage.getContent()),
                entityPage.getTotalPages()
        );
    }
}
