package com.dev.bookly.scheduling.repository.impl;

import com.dev.bookly.scheduling.domains.ResourceShift;
import com.dev.bookly.scheduling.repository.ResourceShiftRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ResourceShiftRepositoryImpl implements ResourceShiftRepository {
    @Override
    public List<ResourceShift> findByResourceId(Long resourceId) {
        return List.of();
    }

    @Override
    public List<ResourceShift> findEffectiveResourceAndDate(Long resourceId, LocalDate date) {
        return List.of();
    }

    @Override
    public ResourceShift save(ResourceShift resourceShift) {
        return null;
    }
}
