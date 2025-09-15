package com.dev.bookly.service.repositories;

import com.dev.bookly.service.domain.Resource;

import java.util.List;

public interface ResourceRepository {
    List<Resource> findByServiceId(Long serviceId);

    Resource save(Resource resource);

    Resource update(Long resourceId, Resource resource);

    void delete(Long resourceId);

    Resource getResourceById(Long resourceId);

    boolean existsByName(String name);


}
