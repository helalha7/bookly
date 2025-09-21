package com.dev.bookly.booking.dtos.responses;

import com.dev.bookly.booking.domains.Booking;
import com.dev.bookly.booking.domains.EBookingSource;
import com.dev.bookly.booking.domains.EBookingStatus;

import java.time.Instant;

public record BookingResponseDTO(
        Long id,
        Long clientId,
        Long resourceId,
        Long resourceShiftId,
        Instant startTime,
        Instant endTime,
        EBookingStatus status,
        EBookingSource source,
        Long policyId,
        Instant createdAt
) {
    public static BookingResponseDTO from(Booking b) {
        return new BookingResponseDTO(
                b.id(),
                b.clientId(),
                b.resourceId(),
                b.resourceShiftId(),
                b.startTime(),
                b.endTime(),
                b.status(),
                b.source(),
                b.policyId(),
                b.createdAt()
        );
    }
}

