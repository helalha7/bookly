package com.dev.bookly.booking.utils.scheduler;

import java.time.Instant;

public interface ReminderScheduler {
    void scheduleReminder(Long bookingId, Instant when);
    void rescheduleReminder(Long bookingId, Instant newWhen);
    void cancelReminder(Long bookingId);
}
