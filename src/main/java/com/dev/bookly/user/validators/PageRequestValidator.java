package com.dev.bookly.user.validators;

import com.dev.bookly.global.pagination.PageRequestDTO;
import com.dev.bookly.global.pagination.PaginationException;

public final class PageRequestValidator {
    public static void validate(PageRequestDTO dto) {
        if(dto.getPage() < 0 || dto.getOffset() < 0)
            throw new PaginationException("invalid pagination input");
    }
}
