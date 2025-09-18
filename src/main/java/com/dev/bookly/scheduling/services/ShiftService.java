package com.dev.bookly.scheduling.services;

import com.dev.bookly.scheduling.dtos.BusinessShiftDTO;
import com.dev.bookly.scheduling.dtos.ResourceShiftDTO;

import java.util.List;


/**
 * Service interface for managing business and resource shifts.
 * Provides methods to list, create, update, and delete shifts at both
 * the business level and the resource level.
 */
public interface ShiftService {

    /**
     * Retrieve all business shifts for a specific business.
     *
     * @param businessId the ID of the business
     * @return a list of BusinessShiftDTO objects (may be empty if no shifts exist)
     */
    List<BusinessShiftDTO> listBusinessShifts(Long businessId);

    /**
     * Create or update a business shift.
     *
     * @param businessId the ID of the business
     * @param dto        the business shift data transfer object
     * @return the saved BusinessShiftDTO
     */
    BusinessShiftDTO upsertBusinessShift(Long businessId ,BusinessShiftDTO dto);

    /**
     * Update an existing business shift.
     *
     * @param businessId the ID of the business
     * @param shiftId    the ID of the shift to update
     * @param dto        the updated business shift data transfer object
     * @return the updated BusinessShiftDTO
     */
    BusinessShiftDTO updateBusinessShift(Long businessId ,Long shiftId ,BusinessShiftDTO dto);

    /**
     * Delete a business shift.
     *
     * @param businessId the ID of the business
     * @param shiftId    the ID of the shift to delete
     */
    void deleteBusinessShift(Long businessId ,Long shiftId);

    /**
     * Retrieve all shifts for a given resource within a service and business.
     *
     * @param businessId the ID of the business
     * @param serviceId  the ID of the service
     * @param resourceId the ID of the resource
     * @return a list of ResourceShiftDTO objects (may be empty if no shifts exist)
     */
    List<ResourceShiftDTO>  listResourceShifts(Long businessId ,Long serviceId , Long resourceId);

    /**
     * Create or update a resource shift.
     *
     * @param businessId the ID of the business
     * @param serviceId  the ID of the service
     * @param resourceId the ID of the resource
     * @param dto        the resource shift data transfer object
     * @return the saved ResourceShiftDTO
     */
    ResourceShiftDTO upsertResourceShift(Long businessId , Long serviceId ,Long resourceId ,ResourceShiftDTO dto);

    /**
     * Update an existing resource shift.
     *
     * @param businessId the ID of the business
     * @param serviceId  the ID of the service
     * @param resourceId the ID of the resource
     * @param shiftId    the ID of the shift to update
     * @param dto        the updated resource shift data transfer object
     * @return the updated ResourceShiftDTO
     */
    ResourceShiftDTO updateResourceShift(Long businessId , Long serviceId ,Long resourceId, Long shiftId ,ResourceShiftDTO dto);

    /**
     * Delete a resource shift.
     *
     * @param businessId the ID of the business
     * @param serviceId  the ID of the service
     * @param resourceId the ID of the resource
     * @param shiftId    the ID of the shift to delete
     */
    void deleteResourceShift(Long businessId , Long serviceId ,Long resourceId ,Long shiftId);
}
