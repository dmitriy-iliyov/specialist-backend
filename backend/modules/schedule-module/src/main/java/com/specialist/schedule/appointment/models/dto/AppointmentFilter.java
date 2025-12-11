package com.specialist.schedule.appointment.models.dto;

import com.specialist.utils.pagination.PageDataHolder;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class AppointmentFilter implements PageDataHolder {

    protected final Boolean scheduled;

    protected final Boolean canceled;

    protected final Boolean completed;

    protected final Boolean asc;

    @NotNull(message = "Page number is required.")
    @PositiveOrZero(message = "Page number should be positive or zero.")
    protected final Integer pageNumber;

    @NotNull(message = "Page size is required.")
    @Min(value = 10, message = "Min page size is 10.")
    @Max(value = 50, message = "Min page size is 50.")
    protected final Integer pageSize;

    @Override
    public Boolean isAsc() {
        return asc;
    }

    @Override
    public Integer getPageNumber() {
        return pageNumber;
    }

    @Override
    public Integer getPageSize() {
        return pageSize;
    }
}
