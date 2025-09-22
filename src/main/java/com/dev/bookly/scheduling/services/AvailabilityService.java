package com.dev.bookly.scheduling.services;

import com.dev.bookly.scheduling.dtos.LocalSlotDTO;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

/**
 * Service interface for managing available resource shifts.
 */

public interface AvailabilityService {

    /**
     * Retrieve all available resource shifts for a specific date and time.
     *
     * @param serviceId the ID of the service
     * @param resourceId the ID of the resource
     * @param date the date we want to check if available
     * @return list of LocalSlotDTO objects
     */
    List<LocalSlotDTO> listSlots(Long serviceId, Long resourceId, String date);

    /**
     * Check if given resourceId , is available in given start dateTime and end dateTime
     *
     * @param resourceId the ID of the resource
     * @param start the date and time we want to check if available start from it
     * @param end the date and time we want to check if available end until it
     * @return true if the resource available in those shifts , else false
     */
    boolean isAvailable(Long resourceId, Instant start , Instant end);

}
