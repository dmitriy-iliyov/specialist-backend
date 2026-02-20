package com.specialist.specialistdirectory.domain.review.models.filters;

import com.specialist.specialistdirectory.domain.review.models.enums.SortType;
import com.specialist.utils.pagination.PageRequest;
import lombok.Getter;

@Getter
public class ReviewSort extends PageRequest {

        protected final SortType sortBy;

        public ReviewSort(Integer pageNumber, Integer pageSize, Boolean asc, SortType sortBy) {
                super(pageNumber, pageSize, asc);
                this.sortBy = sortBy;
        }
}
