package com.dev.bookly.scheduling.services;

import com.dev.bookly.scheduling.dtos.BusinessShiftDTO;
import com.dev.bookly.scheduling.dtos.ResourceShiftDTO;

import java.util.List;

public interface ShiftService {

    List<BusinessShiftDTO> listBusinessShifts(Long businessId);

    BusinessShiftDTO upsertBusinessShift(Long businessId ,BusinessShiftDTO dto);

    BusinessShiftDTO updateBusinessShift(Long businessId ,Long shiftId ,BusinessShiftDTO dto);

    void deleteBusinessShift(Long businessId ,Long shiftId);

    List<ResourceShiftDTO>  listResourceShifts(Long resourceId);

    ResourceShiftDTO upsertResourceShift(Long resourceId ,ResourceShiftDTO dto);
}
