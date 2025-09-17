package com.dev.bookly.scheduling.dtos;

import java.time.LocalTime;

public class BusinessShiftDTO {

    private final Short slotNo;
    private final Short dayOfWeek;
    private final LocalTime startTime;
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
