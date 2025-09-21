package com.dev.bookly.booking.services;

import com.dev.bookly.booking.dtos.requests.CancelBookingRequestDTO;
import com.dev.bookly.booking.dtos.requests.CreateBookingRequestDTO;
import com.dev.bookly.booking.dtos.requests.RescheduleBookingRequestDTO;
import com.dev.bookly.booking.dtos.responses.BookingResponseDTO;

public interface BookingService {
    BookingResponseDTO getById(Long id);
    BookingResponseDTO create(CreateBookingRequestDTO dto);
    BookingResponseDTO reschedule(Long bookingId, RescheduleBookingRequestDTO dto);
    void cancel(Long bookingId, CancelBookingRequestDTO dto);
}
