package com.dev.bookly.booking.dtos.requests;

import java.time.Instant;

public record RescheduleBookingRequestDTO(
        Instant newStartTime,
        Instant newEndTime
) {}
