package com.specialist.schedule.appointment.repositories;

import com.specialist.utils.pagination.PageDataHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public final class PageableUtils {

    public static Pageable generatePageable(PageDataHolder holder) {
        if (holder.isAsc() == null || !holder.isAsc()) {
            return PageRequest.of(
                    holder.getPageNumber(),
                    holder.getPageSize(),
                    Sort.by("date").descending()
            );
        }
        return PageRequest.of(
                holder.getPageNumber(),
                holder.getPageSize(),
                Sort.by("date").ascending()
        );
    }
}
