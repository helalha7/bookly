package com.dev.bookly.booking.services.Impl;


import com.dev.bookly.booking.domains.Booking;
import com.dev.bookly.booking.domains.BookingPolicy;
import com.dev.bookly.booking.domains.EBookingStatus;
import com.dev.bookly.booking.dtos.requests.CancelBookingRequestDTO;
import com.dev.bookly.booking.dtos.requests.CreateBookingRequestDTO;
import com.dev.bookly.booking.dtos.requests.RescheduleBookingRequestDTO;
import com.dev.bookly.booking.dtos.responses.BookingResponseDTO;
import com.dev.bookly.booking.exceptions.*;
import com.dev.bookly.booking.repositories.BookingPolicyRepository;
import com.dev.bookly.booking.repositories.BookingRepository;
import com.dev.bookly.booking.repositories.ResourceShiftRepository;
import com.dev.bookly.booking.services.BookingService;
import com.dev.bookly.booking.utils.scheduler.ReminderScheduler;
import com.dev.bookly.service.domain.Resource;
import com.dev.bookly.service.exceptions.resourcesExceptions.ResourceNotFoundException;
import com.dev.bookly.service.repositories.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepo;
    private final BookingPolicyRepository policyRepo;
    private final ResourceRepository resourceRepo;
    private final ResourceShiftRepository shiftRepo;
    private final ReminderScheduler reminderScheduler;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepo,
                              BookingPolicyRepository policyRepo,
                              ResourceRepository resourceRepo,
                              ResourceShiftRepository shiftRepo,
                              ReminderScheduler reminderScheduler) {
        this.bookingRepo = bookingRepo;
        this.policyRepo = policyRepo;
        this.resourceRepo = resourceRepo;
        this.shiftRepo = shiftRepo;
        this.reminderScheduler = reminderScheduler;
    }

    @Override
    public BookingResponseDTO getById(Long id) {
        Booking b = bookingRepo.findById(id).orElseThrow(() ->
                new BookingNotFoundException("Booking %d not found".formatted(id)));
        return BookingResponseDTO.from(b);
    }

    @Override
    public BookingResponseDTO create(CreateBookingRequestDTO dto) {
        validateRange(dto.startTime(), dto.endTime());
        // ensure resource exists
        Resource resource = resourceRepo.getResourceById(dto.resourceId());
        if (resource == null) {
            throw new ResourceNotFoundException("Resource %d not found".formatted(dto.resourceId()));
        }


        // policy
        BookingPolicy policy = policyRepo.findByServiceId(dto.serviceId())
                .orElseThrow(() -> new PolicyNotFoundException("Policy for service %d not found".formatted(dto.serviceId())));

        // policy checks
        if (!policy.canBook(dto.startTime())) {
            throw new PolicyViolationException("Booking in advance window not allowed");
        }

        // overlap check
        List<Booking> overlaps = bookingRepo.findOverlapping(dto.resourceId(), dto.startTime(), dto.endTime());
        if (!overlaps.isEmpty()) {
            throw new DoubleBookingDetectedException("Time slot overlaps with existing booking");
        }

        // optional: check shift availability/capacity
        boolean shiftOk = shiftRepo.findEffectiveByResourceAndDate(dto.resourceId(), dto.startTime())
                .stream().anyMatch(s -> s.covers(dto.startTime(), dto.endTime()));
        if (!shiftOk) {
            throw new SlotUnavailableException("No effective shift covers the requested interval");
        }

        Booking booking = new Booking(null, dto.clientId(), dto.resourceId(), dto.resourceShiftId(),
                dto.startTime(), dto.endTime(), EBookingStatus.CONFIRMED, dto.source(),
                policy.id(), Instant.now());

        Booking saved = bookingRepo.save(booking);

        // schedule reminder (e.g., 2 hours before)
        reminderScheduler.scheduleReminder(saved.id(), saved.startTime().minus(Duration.ofHours(2)));

        return BookingResponseDTO.from(saved);
    }

    @Override
    public BookingResponseDTO reschedule(Long bookingId, RescheduleBookingRequestDTO dto) {
        validateRange(dto.newStartTime(), dto.newEndTime());

        Booking existing = bookingRepo.findById(bookingId).orElseThrow(() ->
                new BookingNotFoundException("Booking %d not found".formatted(bookingId)));

        BookingPolicy policy = policyRepo.findByServiceId(existing.policyIdService())
                .orElseThrow(() -> new PolicyNotFoundException("Policy for service not found"));

        if (!policy.canReschedule(existing.startTime(), Instant.now())) {
            throw new PolicyViolationException("Reschedule window exceeded");
        }

        List<Booking> overlaps = bookingRepo.findOverlapping(existing.resourceId(), dto.newStartTime(), dto.newEndTime());
        // exclude self if repo includes it
        overlaps.removeIf(b -> b.id().equals(existing.id()));
        if (!overlaps.isEmpty()) {
            throw new DoubleBookingDetectedException("New slot overlaps with another booking");
        }

        existing.reschedule(dto.newStartTime(), dto.newEndTime());
        Booking saved = bookingRepo.save(existing);
        reminderScheduler.rescheduleReminder(saved.id(), saved.startTime());
        return BookingResponseDTO.from(saved);
    }

    @Override
    public void cancel(Long bookingId, CancelBookingRequestDTO dto) {
        Booking existing = bookingRepo.findById(bookingId).orElseThrow(() ->
                new BookingNotFoundException("Booking %d not found".formatted(bookingId)));

        BookingPolicy policy = policyRepo.findByServiceId(existing.policyIdService())
                .orElseThrow(() -> new PolicyNotFoundException("Policy for service not found"));

        if (!policy.canCancel(existing.startTime(), Instant.now())) {
            throw new PolicyViolationException("Cancel window exceeded");
        }

        existing.cancel(dto.reason());
        bookingRepo.save(existing);
        reminderScheduler.cancelReminder(existing.id());
    }

    private static void validateRange(Instant start, Instant end) {
        if (start == null || end == null || !end.isAfter(start)) {
            throw new InvalidDateRangeException("Invalid time range");
        }
    }
}
