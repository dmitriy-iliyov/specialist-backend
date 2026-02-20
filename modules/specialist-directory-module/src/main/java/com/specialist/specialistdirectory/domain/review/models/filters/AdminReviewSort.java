package com.specialist.specialistdirectory.domain.review.models.filters;

import com.specialist.specialistdirectory.domain.review.models.enums.ReviewStatus;
import com.specialist.specialistdirectory.domain.review.models.enums.SortType;
import lombok.Getter;

@Getter
public class AdminReviewSort extends ReviewSort {

    protected final ReviewStatus status;

    public AdminReviewSort(Integer pageNumber, Integer pageSize, Boolean asc, SortType sortBy, ReviewStatus status) {
        super(pageNumber, pageSize, asc, sortBy);
        this.status = status;
    }
}
