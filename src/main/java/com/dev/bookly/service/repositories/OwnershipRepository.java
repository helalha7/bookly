package com.dev.bookly.service.repositories;


public interface OwnershipRepository {

    boolean userOwnsBusiness(Long businessId, String username);

    boolean  userOwnsService(Long businessId, Long serviceId, String username);

    boolean userOwnsResource(Long businessId, Long serviceId, Long resourceId, String username);
}
