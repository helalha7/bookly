package com.dev.bookly.scheduling.validators;

import com.dev.bookly.scheduling.dtos.BusinessShiftDTO;
import com.dev.bookly.scheduling.dtos.ResourceShiftDTO;
import com.dev.bookly.scheduling.exceptions.InvalidShiftTimeException;

public class ShiftValidator {

    /**
     * Validate a BusinessShiftDTO according to basic rules:
     * 1. Day of week must be 1–7.
     * 2. Slot number must be positive.
     * 3. Start and end times must not be null, and start < end.
     *
     * @param dto the BusinessShiftDTO to validate
     */
    public static void validateBusinessShift(BusinessShiftDTO dto) {
        if (dto.getDayOfWeek() < 1 || dto.getDayOfWeek() > 7) {
            throw new InvalidShiftTimeException("Invalid day of week: " + dto.getDayOfWeek());
        }

        if (dto.getSlotNo() <= 0) {
            throw new InvalidShiftTimeException("Slot number must be positive");
        }

        if (dto.getStartTime() == null || dto.getEndTime() == null) {
            throw new InvalidShiftTimeException("Start time and end time cannot be null");
        }

        if (!dto.getStartTime().isBefore(dto.getEndTime())) {
            throw new InvalidShiftTimeException("Shift start time must be before end time");
        }
    }

    /**
     * Validate a ResourceShiftDTO according to basic rules:
     * 1. Day of week must be 1–7.
     * 2. Slot number must be positive.
     * 3. Start and end times must not be null, and start < end.
     * 4. from and to dates must not be null, and from < to.
     * @param dto the ResourceShiftDTO to validate
     */
    public static void validateResourceShift(ResourceShiftDTO dto) {
        if (dto.getDayOfWeek() < 1 || dto.getDayOfWeek() > 7) {
            throw new InvalidShiftTimeException("Invalid day of week: " + dto.getDayOfWeek());
        }

        if (dto.getSlotNo() <= 0) {
            throw new InvalidShiftTimeException("Slot number must be positive");
        }

        if (dto.getStartTime() == null || dto.getEndTime() == null) {
            throw new InvalidShiftTimeException("Start time and end time cannot be null");
        }

        if (!dto.getStartTime().isBefore(dto.getEndTime())) {
            throw new InvalidShiftTimeException("Shift start time must be before end time");
        }

        // Validate effective dates
        if (dto.getEffectiveFrom() != null || dto.getEffectiveTo() != null) {
            if (dto.getEffectiveFrom() == null || dto.getEffectiveTo() == null) {
                throw new InvalidShiftTimeException("Both effectiveFrom and effectiveTo must be provided");
            }
            if (dto.getEffectiveFrom().isAfter(dto.getEffectiveTo())) {
                throw new InvalidShiftTimeException("effectiveFrom must be before or equal to effectiveTo");
            }
        }
    }
}
