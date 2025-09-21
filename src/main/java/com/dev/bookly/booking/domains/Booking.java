package com.dev.bookly.booking.domains;

import java.time.Instant;
import java.util.Objects;

public record Booking(
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
    public void confirm() { /* no-op for now */ }

    public void cancel(String reason) {
        // you can persist reason in an audit table if needed
        setStatus(EBookingStatus.CANCELED);
    }

    public void reschedule(Instant newStart, Instant newEnd) {
        setStatus(EBookingStatus.RESCHEDULED);
        setStartTime(newStart);
        setEndTime(newEnd);
    }

    // Record mutability helpers (copy-on-write style)
    private void setStatus(EBookingStatus st) {
        // records are immutable; emulate mutation with reflection or (preferably)
        // create a builder/new instance in repository save(). For simplicity we rely on repo to persist fields.
        // If you want pure immutability, return a new Booking from methods instead.
        throw new UnsupportedOperationException("Use builder/new Booking instance in repository to persist changes");
    }

    private void setStartTime(Instant t) { throw new UnsupportedOperationException(); }
    private void setEndTime(Instant t) { throw new UnsupportedOperationException(); }

    // Helper used by service when saving (create a modified instance)
    public Booking with(EBookingStatus status, Instant start, Instant end) {
        return new Booking(id, clientId, resourceId, resourceShiftId, start, end, status, source, policyId, createdAt);
    }

    public Long policyIdService() { return policyId; }

    @Override public boolean equals(Object o){ return o instanceof Booking b && Objects.equals(id,b.id); }
    @Override public int hashCode(){ return Objects.hashCode(id); }
}
