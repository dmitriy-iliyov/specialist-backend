package com.specialist.specialistdirectory.domain.specialist.models.filters;

import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistState;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistStatus;
import com.specialist.utils.pagination.PageDataHolder;
import lombok.Getter;

@Getter
public class SystemSpecialistFilter extends BaseSpecialistFilter {

    private final SpecialistStatus status;
    private final SpecialistState state;

    public SystemSpecialistFilter(Boolean asc, Integer pageNumber, Integer pageSize, SpecialistStatus status,
                                  SpecialistState state) {
        super(asc, pageNumber, pageSize);
        this.status = status;
        this.state = state;
    }

    public SystemSpecialistFilter(PageDataHolder pageDataHolder, SpecialistStatus status, SpecialistState state) {
        super(pageDataHolder);
        this.status = status;
        this.state = state;
    }

    @Override
    public boolean isEmpty() {
        return status == null &&
                state == null;
    }
}
