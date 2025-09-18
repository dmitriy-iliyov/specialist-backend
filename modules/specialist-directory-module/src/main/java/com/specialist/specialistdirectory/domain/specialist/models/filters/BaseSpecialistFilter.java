package com.specialist.specialistdirectory.domain.specialist.models.filters;

import com.specialist.utils.pagination.PageDataHolder;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public abstract class BaseSpecialistFilter implements BaseFilter {

    protected final Boolean asc;

    @NotNull(message = "Page number is required.")
    @PositiveOrZero(message = "Page number should be positive or zero.")
    protected final Integer pageNumber;

    @NotNull(message = "Page size is required.")
    @Min(value = 10, message = "Min page size is 10.")
    @Max(value = 50, message = "Min page size is 50.")
    protected final Integer pageSize;

    public BaseSpecialistFilter(Boolean asc, Integer pageNumber, Integer pageSize) {
        this.asc = asc;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public BaseSpecialistFilter(PageDataHolder pageDataHolder) {
        this.asc = pageDataHolder.asc();
        this.pageNumber = pageDataHolder.pageNumber();
        this.pageSize = pageDataHolder.pageSize();
    }

    @Override
    public Boolean asc() {
        return asc;
    }

    @Override
    public Integer pageNumber() {
        return pageNumber;
    }

    @Override
    public Integer pageSize() {
        return pageSize;
    }
}
