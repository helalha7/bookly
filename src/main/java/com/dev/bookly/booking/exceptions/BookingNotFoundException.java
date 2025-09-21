package com.dev.bookly.booking.exceptions;

public class BookingNotFoundException extends RuntimeException {
    public BookingNotFoundException(String message) { super(message); }
}
