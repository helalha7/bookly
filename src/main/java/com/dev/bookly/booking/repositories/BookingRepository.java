package com.dev.bookly.booking.repositories;

import com.dev.bookly.booking.domains.Booking;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface BookingRepository {
    Optional<Booking> findById(Long id);
    List<Booking> findOverlapping(Long resourceId, Instant start, Instant end);
    Booking save(Booking booking);
}
