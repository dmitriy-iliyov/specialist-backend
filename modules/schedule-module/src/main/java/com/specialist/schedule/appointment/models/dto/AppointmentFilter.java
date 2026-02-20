package com.specialist.schedule.appointment.models.dto;

import com.specialist.utils.pagination.PageRequest;
import lombok.Getter;

@Getter
public class AppointmentFilter extends PageRequest {

    protected final Boolean scheduled;

    protected final Boolean canceled;

    protected final Boolean completed;

    public AppointmentFilter(Integer pageNumber, Integer pageSize, Boolean asc, Boolean scheduled, Boolean canceled,
                             Boolean completed) {
        super(pageNumber, pageSize, asc);
        this.scheduled = scheduled;
        this.canceled = canceled;
        this.completed = completed;
    }
}
