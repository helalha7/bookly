package com.dev.bookly.scheduling.dtos;

import com.dev.bookly.scheduling.domains.ResourceShift;

public class ResourceShiftMapper {

    public static ResourceShift toResourceShift(Long resourceId , ResourceShiftDTO dto) {
        ResourceShift resourceShift = new ResourceShift(
                null,
                resourceId,
                dto.getSlotNo(),
                dto.getDayOfWeek(),
                dto.getStartTime(),
                dto.getEndTime(),
                dto.getEffectiveFrom(),
                dto.getEffectiveTo()
        );
        return resourceShift;
    }


    public static ResourceShiftDTO toResourceShiftDTO(ResourceShift shift) {
        ResourceShiftDTO  dto = new ResourceShiftDTO(
                shift.getSlotNo(),
                shift.getDayOfWeek(),
                shift.getStartTime(),
                shift.getEndTime(),
                shift.getEffectiveFrom(),
                shift.getEffectiveTo()
        );
        return dto;
    }

}
