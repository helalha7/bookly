package com.dev.bookly.booking.controllers;


import com.dev.bookly.booking.dtos.requests.CancelBookingRequestDTO;
import com.dev.bookly.booking.dtos.requests.CreateBookingRequestDTO;
import com.dev.bookly.booking.dtos.requests.RescheduleBookingRequestDTO;
import com.dev.bookly.booking.dtos.responses.BookingResponseDTO;
import com.dev.bookly.booking.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> getBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getById(id));
    }

    @PostMapping
    public ResponseEntity<BookingResponseDTO> create(@RequestBody CreateBookingRequestDTO dto) {
        return ResponseEntity.ok(bookingService.create(dto));
    }

    @PutMapping("/{id}/reschedule")
    public ResponseEntity<BookingResponseDTO> reschedule(@PathVariable Long id,
                                                         @RequestBody RescheduleBookingRequestDTO dto) {
        return ResponseEntity.ok(bookingService.reschedule(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancel(@PathVariable Long id,
                                       @RequestBody CancelBookingRequestDTO dto) {
        bookingService.cancel(id, dto);
        return ResponseEntity.noContent().build();
    }
}
