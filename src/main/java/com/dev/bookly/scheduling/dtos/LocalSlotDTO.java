package com.dev.bookly.scheduling.dtos;

import java.time.LocalDateTime;

public class LocalSlotDTO {
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final int available;

    public LocalSlotDTO(LocalDateTime start, LocalDateTime end, int available) {
        this.start = start;
        this.end = end;
        this.available = available;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public int getAvailable() {
        return available;
    }
}
