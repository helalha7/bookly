package com.dev.bookly.booking.domains;

import java.time.Instant;

public record ResourceShift(Long id, Long resourceId, Instant start, Instant end, int capacity) {
    public boolean covers(Instant s, Instant e) {
        return !s.isBefore(start) && !e.isAfter(end);
    }
}
