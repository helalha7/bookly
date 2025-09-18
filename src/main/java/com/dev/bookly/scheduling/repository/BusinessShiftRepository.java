package com.dev.bookly.scheduling.repository;

import com.dev.bookly.scheduling.domains.BusinessShift;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing business shifts.
 * Provides methods for CRUD operations and overlap detection.
 */
public interface BusinessShiftRepository {

    /**
     * Find all shifts belonging to a specific business.
     *
     * @param businessId the ID of the business
     * @return a list of BusinessShift entities (may be empty if no shifts exist)
     */
    List<BusinessShift> findByBusinessId(Long businessId);

    /**
     * Save a new business shift into the database.
     *
     * @param businessShift the shift entity to save
     * @return the saved BusinessShift
     */
    BusinessShift save(BusinessShift businessShift);

    /**
     * Update an existing shift belonging to a business.
     *
     * @param shiftId       the ID of the shift to update
     * @param businessShift the updated shift entity
     * @return the number of rows affected (0 if no shift was updated)
     */
    int update(Long shiftId , BusinessShift businessShift);

    /**
     * Delete a business shift by its ID and business ID.
     *
     * @param shiftId    the ID of the shift to delete
     * @param businessId the ID of the business that owns the shift
     * @return the number of rows affected (0 if no shift was deleted)
     */
    int delete(Long shiftId , Long businessId);

    /**
     * Check if a new shift overlaps with existing shifts for the same business and day.
     * Two shifts are considered overlapping if their time ranges intersect.
     *
     * @param businessId the ID of the business
     * @param dayOfWeek  the day of the week (0-6 or 1-7 depending on your convention)
     * @param startTime  the start time of the new shift
     * @param endTime    the end time of the new shift
     * @return true if there is an overlap, false otherwise
     */
    boolean existsOverlap(Long businessId, short dayOfWeek, LocalTime startTime, LocalTime endTime);

    /**
     * Find a specific business shift by its ID and business ID.
     *
     * @param businessId the ID of the business
     * @param shiftId    the ID of the shift
     * @return an Optional containing the BusinessShift if found, otherwise empty
     */
    Optional<BusinessShift> findBusinessShiftById(Long businessId, Long shiftId);

}
