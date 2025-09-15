package com.dev.bookly.service.servises;

public interface OwnershipService {

    void userOwnsBusiness(Long businessId,String username);

    void userOwnsService(Long businessId, Long servicesId, String username);

    void userOwnsResource(Long businessId, Long servicesId,Long resourceId, String username);

}
