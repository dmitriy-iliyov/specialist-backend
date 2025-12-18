package com.specialist.specialistdirectory.domain.review.models.enums;

import com.specialist.specialistdirectory.exceptions.UnknownSortTypeException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum SortType {
    r("rating"),
    c_at("createdAt");

    private final String columnName;

    SortType(String columnName) {
        this.columnName = columnName;
    }

    public static SortType fromJson(String json) {
        return Arrays.stream(SortType.values())
                .filter(value -> value.name().equals(json))
                .findFirst()
                .orElseThrow(UnknownSortTypeException::new);
    }
}
