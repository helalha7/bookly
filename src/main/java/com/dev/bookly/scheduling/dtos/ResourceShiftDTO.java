package com.dev.bookly.scheduling.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

public class ResourceShiftDTO {

    private final Short slotNo;
    private final Short dayOfWeek;
    private final LocalTime startTime;
    private final LocalTime endTime;
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
