package com.dev.bookly.global.pagination;

import java.util.List;

public class PageResult<T> {
    private final List<T> rows;
    private final long total;

    public PageResult(List<T> rows, long total) {
        this.rows = rows;
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public long getTotal() {
        return total;
    }
}
