package com.dev.bookly.scheduling.repository;

import com.dev.bookly.scheduling.domains.BusinessShift;

import java.util.List;

public interface BusinessShiftRepository {

    List<BusinessShift> findByBusinessId(Long businessId);

    BusinessShift save(BusinessShift businessShift);

    BusinessShift update(Long shiftId , BusinessShift businessShift);

    void delete(Long shiftId , Long businessId);
}
