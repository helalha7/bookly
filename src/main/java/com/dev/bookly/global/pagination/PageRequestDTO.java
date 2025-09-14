package com.dev.bookly.global.pagination;

public class PageRequestDTO {
    private final int page;
    private final int size;
    public static final int MAX_SIZE = 200;

    public PageRequestDTO(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public int getOffset() {
        return page * size;
    }
}
