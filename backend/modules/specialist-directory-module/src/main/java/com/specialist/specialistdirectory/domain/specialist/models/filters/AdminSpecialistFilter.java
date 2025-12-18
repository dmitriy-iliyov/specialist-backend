package com.specialist.specialistdirectory.domain.specialist.models.filters;

import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistLanguage;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistState;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistStatus;
import com.specialist.specialistdirectory.domain.specialist.validation.SpecialistFilter;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@SpecialistFilter
@Getter
public class AdminSpecialistFilter extends ExtendedSpecialistFilter {

    protected final SpecialistStatus status;
    protected final SpecialistState state;
    protected final Boolean aggregate;

    public AdminSpecialistFilter(String city, String cityCode, Long typeId, SpecialistLanguage lang, Integer minRating,
                                 Integer maxRating, String firstName, String secondName, String lastName, SpecialistStatus status,
                                 SpecialistState state, Boolean aggregate, Boolean asc, Integer pageNumber, Integer pageSize) {
        super(city, cityCode, typeId, lang, minRating, maxRating, firstName, secondName, lastName, asc, pageNumber, pageSize);
        this.status = status;
        this.state = state;
        this.aggregate = aggregate;
    }
}
