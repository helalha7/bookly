package com.dev.bookly.booking.exceptions;

public class DoubleBookingDetectedException extends RuntimeException {
    public DoubleBookingDetectedException(String message) { super(message); }
}
