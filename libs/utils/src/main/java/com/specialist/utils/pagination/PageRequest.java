package com.specialist.utils.pagination;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageRequest implements PageDataHolder {

        @NotNull(message = "Page number is required.")
        @PositiveOrZero(message = "Page number should be positive or zero.")
        protected final Integer pageNumber;

        @NotNull(message = "Page size is required.")
        @Min(value = 10, message = "Min page size is 10.")
        @Max(value = 50, message = "Min page size is 50.")
        protected final Integer pageSize;

        protected final Boolean asc;

        public String cacheKey() {
                return "a:" + asc + ":n:" + pageNumber + ":s:" + pageSize;
        }

        @Override
        public Boolean isAsc() {
                return asc;
        }

        public PageRequest(PageDataHolder holder) {
                this.asc = holder.isAsc();
                this.pageNumber = holder.getPageNumber();
                this.pageSize = holder.getPageSize();
        }
}