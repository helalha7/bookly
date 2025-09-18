package com.dev.bookly.scheduling.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class ResourceShiftDTO {

    @Min(value = 1, message = "Slot number must be at least 1")
    private final Short slotNo;

    @Min(value = 1, message = "Day of week must be between 1 and 7")
    @Max(value = 7, message = "Day of week must be between 1 and 7")
    private final Short dayOfWeek;

    @NotNull(message = "Start time cannot be null")
    private final LocalTime startTime;

    @NotNull(message = "End time cannot be null")
    private final LocalTime endTime;
    
    @NotNull(message = "Effective from date cannot be null")
    private final LocalDate effectiveFrom;

    private final LocalDate effectiveTo;

    public ResourceShiftDTO(Short slotNo, Short dayOfWeek, LocalTime startTime, LocalTime endTime, LocalDate effectiveFrom, LocalDate effectiveTo) {
        this.slotNo = slotNo;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.effectiveFrom = effectiveFrom;
        this.effectiveTo = effectiveTo;
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

    public LocalDate getEffectiveFrom() {
        return effectiveFrom;
    }

    public LocalDate getEffectiveTo() {
        return effectiveTo;
    }
}
