package com.dev.bookly.booking.domains;

import java.time.Duration;
import java.time.Instant;

public record BookingPolicy(
        Long id,
        Long serviceId,
        int maxAdvanceDays,
        int cancelWindowHours,
        boolean rescheduleAllowed,
        Instant createdAt
) {
    public boolean canBook(Instant start) {
        return start.isBefore(Instant.now().plus(Duration.ofDays(maxAdvanceDays)));
    }

    public boolean canCancel(Instant start, Instant now) {
        return Duration.between(now, start).toHours() >= cancelWindowHours;
    }

    public boolean canReschedule(Instant start, Instant now) {
        return rescheduleAllowed && canCancel(start, now);
    }
}
