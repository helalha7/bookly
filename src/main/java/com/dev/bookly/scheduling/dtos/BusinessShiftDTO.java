package com.dev.bookly.scheduling.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public class BusinessShiftDTO {

    @Min(value = 1, message = "Slot number must be at least 1")
    private final Short slotNo;

    @Min(value = 1, message = "Day of week must be between 1 and 7")
    @Max(value = 7, message = "Day of week must be between 1 and 7")
    private final Short dayOfWeek;

    @NotNull(message = "Start time cannot be null")
    private final LocalTime startTime;

    @NotNull(message = "End time cannot be null")
    private final LocalTime endTime;

    public BusinessShiftDTO(Short slotNo, Short dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.slotNo = slotNo;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Short getSlotNo() {
        return slotNo;
    }

    public Short getDayOfWeek() {
        return dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}
