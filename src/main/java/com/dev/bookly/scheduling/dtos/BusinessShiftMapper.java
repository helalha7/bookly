package com.dev.bookly.scheduling.dtos;

import com.dev.bookly.scheduling.domains.BusinessShift;
import com.dev.bookly.service.domain.Resource;
import com.dev.bookly.service.dtos.requests.ResourceRequestDTO;
import com.dev.bookly.service.dtos.responses.ResourceResponseDTO;

public class BusinessShiftMapper {

    public static BusinessShift toBusinessShift(Long businessId , BusinessShiftDTO dto) {
        BusinessShift businessShift = new BusinessShift(
                null,
                businessId,
                dto.getSlotNo(),
                dto.getDayOfWeek(),
                dto.getStartTime(),
                dto.getEndTime()
        );
        return businessShift;
    }


    public static BusinessShiftDTO toBusinessShiftDTO(BusinessShift shift) {
        BusinessShiftDTO  dto = new BusinessShiftDTO(
                shift.getSlotNo(),
                shift.getDayOfWeek(),
                shift.getStartTime(),
                shift.getEndTime()
        );
        return dto;
    }
}
