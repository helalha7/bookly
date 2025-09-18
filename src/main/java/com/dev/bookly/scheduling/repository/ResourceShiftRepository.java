package com.dev.bookly.scheduling.repository;

import com.dev.bookly.scheduling.domains.ResourceShift;

import java.time.LocalDate;
import java.util.List;

public interface ResourceShiftRepository {

    List<ResourceShift> findByResourceId(Long resourceId);

    List<ResourceShift> findEffectiveResourceAndDate(Long resourceId , LocalDate date);

    ResourceShift save(ResourceShift resourceShift);

    ResourceShift update(Long shiftId , ResourceShift resourceShift);

    void delete(Long resourceId , Long shiftId);
}
