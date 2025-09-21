package com.dev.bookly.booking.exceptions;

public class SlotUnavailableException extends RuntimeException {
    public SlotUnavailableException(String message) { super(message); }
}
