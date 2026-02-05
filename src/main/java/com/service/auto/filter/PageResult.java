package com.service.auto.filter;

import java.util.List;

public record PageResult<T>(

        List<T> content,
        long totalElements,
        int page,
        int size
) {

    public int totalPages() {
        return (int) Math.ceil((double) totalElements / (double) size);
    }
}
