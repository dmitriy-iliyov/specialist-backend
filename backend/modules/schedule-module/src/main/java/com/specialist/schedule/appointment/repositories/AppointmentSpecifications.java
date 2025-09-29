package com.specialist.schedule.appointment.repositories;

import com.specialist.schedule.appointment.models.AppointmentEntity;
import com.specialist.schedule.appointment.models.dto.AppointmentFilter;
import com.specialist.schedule.appointment.models.enums.AppointmentStatus;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class AppointmentSpecifications {

    public static Specification<AppointmentEntity> hasStatuses(AppointmentFilter filter) {
        List<AppointmentStatus> statuses = new ArrayList<>();
        if (filter.getScheduled()) statuses.add(AppointmentStatus.SCHEDULED);
        if (filter.getCompleted()) statuses.add(AppointmentStatus.COMPLETED);
        if (filter.getCanceled())  statuses.add(AppointmentStatus.CANCELED);

        if (statuses.isEmpty()) {
            return Specification.where(null);
        }
        return (r, q, cb) -> r.get("status").in(statuses);
    }

    public static Specification<AppointmentEntity> hasSpecialistId(UUID specialistId) {
        return (r, q, cb) -> cb.equal(r.get("specialistId"), specialistId);
    }

    public static Specification<AppointmentEntity> hasUserId(UUID userId) {
        return (r, q, cb) -> cb.equal(r.get("userId"), userId);
    }
}
