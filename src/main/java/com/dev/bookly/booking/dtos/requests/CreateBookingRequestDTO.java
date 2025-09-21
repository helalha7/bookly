package com.dev.bookly.booking.dtos.requests;


import com.dev.bookly.booking.domains.EBookingSource;

import java.time.Instant;

public record CreateBookingRequestDTO(
        Long clientId,
        Long serviceId,
        Long resourceId,
        Long resourceShiftId,
        Instant startTime,
        Instant endTime,
        EBookingSource source // whatsapp | admin
) {}
