package com.dev.bookly.scheduling.repository;



/**
 * Repository interface for verifying ownership of businesses, shifts, and resources.
 * Used to enforce access control rules by checking whether a given user
 * owns the entity they are trying to manage.
 */
public interface ScheduleOwnershipRepository {

    /**
     * Check if a user owns a specific business.
     *
     * @param businessId the ID of the business
     * @param userId     the ID of the user
     * @return true if the user owns the business, false otherwise
     */
    boolean userOwnsBusiness(Long businessId, Long userId);


    /**
     * Check if a user owns a specific business shift.
     *
     * @param businessId the ID of the business
     * @param shiftId    the ID of the shift
     * @param userId     the ID of the user
     * @return true if the user owns the business shift, false otherwise
     */
    boolean userOwnsBusinessShift(Long businessId, Long shiftId , Long userId);

    /**
     * Check if a user owns a specific resource within a service and business.
     *
     * @param businessId the ID of the business
     * @param serviceId  the ID of the service
     * @param resourceId the ID of the resource
     * @param userId     the ID of the user
     * @return true if the user owns the resource, false otherwise
     */
    boolean userOwnsResource(Long businessId, Long serviceId, Long resourceId, Long userId);

    /**
     * Check if a user owns a specific resource shift under a service and business.
     *
     * @param businessId the ID of the business
     * @param serviceId  the ID of the service
     * @param resourceId the ID of the resource
     * @param shiftId    the ID of the shift
     * @param userId     the ID of the user
     * @return true if the user owns the resource shift, false otherwise
     */
    boolean userOwnsResourceShift(Long businessId, Long serviceId, Long resourceId, Long shiftId ,Long userId);
}
