package com.specialist.specialistdirectory.domain.specialist.models.filters;

import com.specialist.utils.pagination.BaseFilter;
import com.specialist.utils.pagination.PageDataHolder;
import com.specialist.utils.pagination.PageRequest;

public abstract class BaseSpecialistFilter extends PageRequest implements BaseFilter {

    public BaseSpecialistFilter(Integer pageNumber, Integer pageSize, Boolean asc) {
        super(pageNumber, pageSize, asc);
    }

    public BaseSpecialistFilter(PageDataHolder holder) {
        super(holder);
    }
}
