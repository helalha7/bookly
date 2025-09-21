package com.dev.bookly.booking.repositories;

import com.dev.bookly.booking.domains.ResourceShift;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface ResourceShiftRepository {
    List<ResourceShift> findEffectiveByResourceAndDate(Long resourceId, Instant pointInTime);
    Optional<ResourceShift> findById(Long id);
}

