package com.specialist.specialistdirectory.domain.specialist.models.filters;

import com.specialist.utils.pagination.BaseFilter;
import com.specialist.utils.pagination.PageDataHolder;
import com.specialist.utils.pagination.PageRequest;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public abstract class BaseSpecialistFilter extends PageRequest implements BaseFilter {

    public BaseSpecialistFilter(Integer pageNumber, Integer pageSize, Boolean asc) {
        super(pageNumber, pageSize, asc);
    }

    public BaseSpecialistFilter(PageDataHolder holder) {
        super(holder);
    }
}
