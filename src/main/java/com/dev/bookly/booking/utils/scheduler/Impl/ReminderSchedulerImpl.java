package com.dev.bookly.booking.utils.scheduler.Impl;

import com.dev.bookly.booking.utils.scheduler.ReminderScheduler;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Logger;

@Component
public class ReminderSchedulerImpl implements ReminderScheduler {

    private static final Logger log = (Logger) LoggerFactory.getLogger(ReminderSchedulerImpl.class);

    private TaskScheduler scheduler;
    private final Map<Long, ScheduledFuture<?>> reminders = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        ThreadPoolTaskScheduler ts = new ThreadPoolTaskScheduler();
        ts.setPoolSize(4);
        ts.setThreadNamePrefix("reminder-");
        ts.initialize();
        this.scheduler = ts;
    }

    @PreDestroy
    public void shutdown() {
        if (scheduler instanceof ThreadPoolTaskScheduler ts) {
            ts.shutdown();
        }
    }

    @Override
    public void scheduleReminder(Long bookingId, Instant when) {
        cancelReminder(bookingId); // ensure no duplicates
        log.info("Scheduling reminder for booking {} at {}");

        ScheduledFuture<?> future = scheduler.schedule(() -> {
            // TODO: hook into notification service (email, WhatsApp, etc.)
            log.info(">>> Sending reminder for booking {}");
        }, when);

        reminders.put(bookingId, future);
    }

    @Override
    public void rescheduleReminder(Long bookingId, Instant newWhen) {
        log.info("Rescheduling reminder for booking {} to {}");
        scheduleReminder(bookingId, newWhen);
    }

    @Override
    public void cancelReminder(Long bookingId) {
        ScheduledFuture<?> future = reminders.remove(bookingId);
        if (future != null && !future.isDone()) {
            log.info("Cancelling reminder for booking {}");
            future.cancel(false);
        }
    }
}
