package com.dev.bookly.booking.repositories;

import com.dev.bookly.booking.domains.BookingPolicy;

import java.util.Optional;

public interface BookingPolicyRepository {
    Optional<BookingPolicy> findByServiceId(Long serviceId);
}
