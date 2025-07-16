package com.aidcompass.specialistdirectory.domain.review.models.enums;

import lombok.Getter;

@Getter
public enum SortType {
    r("rating"),
    c_at("createdAt");

    private final String columnName;

    SortType(String columnName) {
        this.columnName = columnName;
    }
}
