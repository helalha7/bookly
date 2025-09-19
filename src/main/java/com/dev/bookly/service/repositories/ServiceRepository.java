package com.dev.bookly.service.repositories;



import com.dev.bookly.global.pagination.PageResult;
import com.dev.bookly.service.domain.Service;

import java.util.List;

public interface ServiceRepository {
    public PageResult<Service> findByBusiness(Long businessId,int offset, int limit);
    public Service findById(Long id);
    public boolean existsByName(Long businessId, String name);
    public Service save(Service s);

    com.dev.bookly.service.domain.Service update(com.dev.bookly.service.domain.Service s);

    void delete(Long serviceId);
}
