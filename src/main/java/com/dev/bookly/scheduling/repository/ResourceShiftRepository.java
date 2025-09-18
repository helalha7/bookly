package com.dev.bookly.scheduling.repository;

import com.dev.bookly.scheduling.domains.ResourceShift;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


/**
 * Repository interface for managing resource-level shifts.
 * Provides methods for CRUD operations, date-based queries, and overlap detection.
 */
public interface ResourceShiftRepository {

    /**
     * Find all shifts belonging to a specific resource.
     *
     * @param resourceId the ID of the resource
     * @return a list of ResourceShift entities (may be empty if no shifts exist)
     */
    List<ResourceShift> findByResourceId(Long resourceId);

    /**
     * Find all shifts for a given resource that are effective on a specific date.
     * Useful for checking availability of a resource on a given day.
     *
     * @param resourceId the ID of the resource
     * @param date       the date to filter shifts by
     * @return a list of ResourceShift entities effective on the given date
     */
    List<ResourceShift> findEffectiveResourceAndDate(Long resourceId , LocalDate date);

    /**
     * Save a new resource shift into the database.
     *
     * @param resourceShift the shift entity to save
     * @return the saved ResourceShift
     */
    ResourceShift save(ResourceShift resourceShift);

    /**
     * Update an existing resource shift.
     *
     * @param shiftId       the ID of the shift to update
     * @param resourceShift the updated shift entity
     * @return the number of rows affected (0 if no shift was updated)
     */
    int update(Long shiftId , ResourceShift resourceShift);

    /**
     * Delete a resource shift by its ID and resource ID.
     *
     * @param resourceId the ID of the resource
     * @param shiftId    the ID of the shift to delete
     * @return the number of rows affected (0 if no shift was deleted)
     */
    int delete(Long resourceId , Long shiftId);

    /**
     * Check if a new shift overlaps with existing shifts for the same resource and day.
     * Two shifts are considered overlapping if their time ranges intersect.
     *
     * @param resourceId the ID of the resource
     * @param dayOfWeek  the day of the week (0-6 or 1-7 depending on your convention)
     * @param startTime  the start time of the new shift
     * @param endTime    the end time of the new shift
     * @return true if there is an overlap, false otherwise
     */
    boolean existsOverlap(Long resourceId, short dayOfWeek, LocalTime startTime, LocalTime endTime);

    /**
     * Find a specific resource shift by its ID and resource ID.
     *
     * @param resourceId the ID of the resource
     * @param shiftId    the ID of the shift
     * @return an Optional containing the ResourceShift if found, otherwise empty
     */
    Optional<ResourceShift> findResourceShiftById(Long resourceId, Long shiftId);

}