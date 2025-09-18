package com.specialist.specialistdirectory.domain.specialist.models.filters;

import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistStatus;
import com.specialist.utils.pagination.PageDataHolder;
import lombok.Getter;

@Getter
public class SystemSpecialistFilter extends BaseSpecialistFilter {

    private final SpecialistStatus status;

    public SystemSpecialistFilter(Boolean asc, Integer pageNumber, Integer pageSize, SpecialistStatus status) {
        super(asc, pageNumber, pageSize);
        this.status = status;
    }

    public SystemSpecialistFilter(PageDataHolder pageDataHolder, SpecialistStatus status) {
        super(pageDataHolder);
        this.status = status;
    }

    @Override
    public boolean isEmpty() {
        return status == null;
    }
}
