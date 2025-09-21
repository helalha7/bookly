package com.dev.bookly.service.repositories;

import com.dev.bookly.global.pagination.PageResponseDTO;
import com.dev.bookly.global.pagination.PageResult;
import com.dev.bookly.service.domain.Resource;

import java.util.List;

public interface ResourceRepository {
    PageResult<Resource> findByServiceId(Long serviceId, int offset, int limit);

    Resource save(Resource resource);

    Resource update(Long resourceId, Resource resource);

    void delete(Long resourceId);

    Resource getResourceById(Long resourceId);

    boolean existsByName(String name);


}
