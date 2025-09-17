package com.dev.bookly.scheduling.domains;


import java.time.LocalTime;

public class BusinessShift {

    private Long id;
    private Long businessId;
    private Short slotNo;
    private Short dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;


    public BusinessShift(Long id, Long businessId, Short slotNo, Short dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.businessId = businessId;
        this.slotNo = slotNo;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public Short getSlotNo() {
        return slotNo;
    }

    public void setSlotNo(Short slotNo) {
        this.slotNo = slotNo;
    }

    public Short getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Short dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "BusinessShift{" +
                "id=" + id +
                ", businessId=" + businessId +
                ", slotNo=" + slotNo +
                ", dayOfWeek=" + dayOfWeek +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
