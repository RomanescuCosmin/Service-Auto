package com.service.auto.filter;

import java.time.LocalDate;

public record ProgramareFilter(
        Long userId,
        String sort,
        String order,
        Boolean confirmed,
        Boolean canceled,
        LocalDate data
) {
    public boolean hasSort() {
        return sort != null && order != null;
    }
}