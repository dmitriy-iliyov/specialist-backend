package com.aidcompass.utils;

import java.util.List;

public record PageResponse<T> (
    List<T> data,
    int totalPage
) { }
