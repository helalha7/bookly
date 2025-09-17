package com.dev.bookly.service.repositories;



import com.dev.bookly.service.domain.Service;

import java.util.List;

public interface ServiceRepository {
    public List<Service> findByBusiness(Long businessId);
    public Service findById(Long id);
    public boolean existsByName(Long businessId, String name);
    public Service save(Service s);

    com.dev.bookly.service.domain.Service update(com.dev.bookly.service.domain.Service s);
}
