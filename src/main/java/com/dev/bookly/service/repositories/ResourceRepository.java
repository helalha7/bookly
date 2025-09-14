package com.dev.bookly.service.repositories;

import com.dev.bookly.service.domain.Resource;

import java.util.List;

public interface ResourceRepository {
    public List<Resource> findByService(Long serviceId);
    public Resource findById(Long id);

    public boolean existsByName(Long serviceId, String name);

    public Resource save(Resource r);
    Resource update(Long resourceId, Resource resource);


}
