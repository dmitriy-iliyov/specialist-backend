package com.aidcompass.specialistdirectory.domain.review.models.filters;

import lombok.Getter;

@Getter
public enum SortType {
    RATING("r", "rating"),
    CREATED_AT("c_at", "createdAt");

    private final String columnName;
    private final String queryName;


    SortType(String queryName, String columnName) {
        this.queryName = queryName;
        this.columnName = columnName;
    }

}
